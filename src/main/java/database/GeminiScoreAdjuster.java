package database;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Genre;
import entity.Media;
import entity.recommendation.TasteProfile;
import use_case.recommendation.Adjustment;
import use_case.recommendation.ScoreAdjuster;
import use_case.recommendation.ScoreAdjustmentException;

/**
 * Asks Gemini to nudge one candidate's score and explain it in a sentence.
 */
public class GeminiScoreAdjuster implements ScoreAdjuster {

    /**
     * Where the key is read from, matching how the TMDB token is supplied.
     */
    public static final String API_KEY_VARIABLE = "Gemini_API_Key";

    /**
     * Overridable so the model can be changed without a rebuild.
     */
    public static final String MODEL_VARIABLE = "Gemini_Model";

    /**
     * Used when the model variable is not set.
     */
    public static final String DEFAULT_MODEL = "gemini-flash-lite-latest";

    private static final String ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent";

    /**
     * The document's cap, enforced here as well as in the use case.
     */
    private static final double MAX_ADJUSTMENT = 0.05;

    private static final int TIMEOUT_SECONDS = 10;
    private static final int MAX_EXPLANATION_LENGTH = 200;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String apiKey;
    private final String model;

    public GeminiScoreAdjuster() {
        this(System.getenv(API_KEY_VARIABLE), System.getenv(MODEL_VARIABLE));
    }

    public GeminiScoreAdjuster(String apiKey, String model) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
        if (model == null || model.isBlank()) {
            this.model = DEFAULT_MODEL;
        }
        else {
            this.model = model;
        }
    }

    /**
     * Says whether a key is configured, so callers can skip the network entirely rather than failing once per candidate.
     *
     * @return the is configured
     */
    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank();
    }

    @Override
    public Adjustment adjust(Media candidate, TasteProfile tasteProfile) {
        if (!isConfigured()) {
            throw new ScoreAdjustmentException(
                    "No " + API_KEY_VARIABLE + " is set, so no adjustment was requested.");
        }

        try {
            final String reply = send(buildPrompt(candidate, tasteProfile));
            return parse(reply);
        }
        catch (IOException | RuntimeException exception) {
            throw new ScoreAdjustmentException(
                    "Gemini could not be reached or its reply could not be read.", exception);
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new ScoreAdjustmentException("The adjustment request was interrupted.", exception);
        }
    }

    /**
     * Asks about the whole shortlist in one request.
     *
     * @param candidates the candidates
     * @param tasteProfile the taste profile
     * @return the adjust all
     */
    @Override
    public List<Adjustment> adjustAll(List<Media> candidates, TasteProfile tasteProfile) {
        final List<Adjustment> adjustments = new ArrayList<>();

        if (!isConfigured() || candidates.isEmpty()) {
            for (int i = 0; i < candidates.size(); i++) {
                adjustments.add(Adjustment.NONE);
            }
            return adjustments;
        }

        try {
            final String reply = send(buildBatchPrompt(candidates, tasteProfile));
            return parseBatch(reply, candidates.size());
        }
        catch (IOException | InterruptedException | RuntimeException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            for (int i = 0; i < candidates.size(); i++) {
                adjustments.add(Adjustment.NONE);
            }
            return adjustments;
        }
    }

    private String buildBatchPrompt(List<Media> candidates, TasteProfile tasteProfile) {
        final StringBuilder titles = new StringBuilder();
        for (int i = 0; i < candidates.size(); i++) {
            final Media candidate = candidates.get(i);
            titles.append(i).append(". ").append(candidate.getTitle())
                    .append(" (").append(candidate.getReleaseYear()).append("), genres: ")
                    .append(orNone(namesOf(candidate.getGenres())))
                    .append(", audience rating ").append(candidate.getAverageRating())
                    .append("/10\n");
        }

        return "You are refining film recommendations already ranked by a deterministic "
                + "formula. Do not re-rank them yourself.\n\n"
                + "The viewer tends to enjoy these genres: "
                + orNone(namesOf(tasteProfile.getGenres())) + ".\n\n"
                + "Candidates:\n" + titles + "\n"
                + "For each candidate, considering nuance the formula cannot see such as tone "
                + "or theme, reply with strict JSON and nothing else, one entry per candidate "
                + "in the same order:\n"
                + "{\"adjustments\":[{\"index\": <number>, \"delta\": <between -0.05 and 0.05>, "
                + "\"explanation\": \"<one short sentence>\"}]}";
    }

    /**
     * Reads a batch reply, keeping every candidate lined up with its own entry.
     *
     * @param reply the reply
     * @param expected the expected
     * @return the parse batch
     * @throws IOException if the operation fails
     */
    private List<Adjustment> parseBatch(String reply, int expected) throws IOException {
        final List<Adjustment> adjustments = new ArrayList<>();
        for (int i = 0; i < expected; i++) {
            adjustments.add(Adjustment.NONE);
        }

        final JsonNode parsed = objectMapper.readTree(extractJson(reply));
        for (JsonNode entry : parsed.path("adjustments")) {
            final int index = entry.path("index").asInt(-1);
            // An index outside the shortlist is ignored rather than trusted, so
            // a strange reply cannot touch a candidate that was never offered.
            if (index >= 0 && index < expected) {
                adjustments.set(index, toAdjustment(entry));
            }
        }
        return adjustments;
    }

    private Adjustment toAdjustment(JsonNode entry) {
        String explanation = entry.path("explanation").asText("");
        if (explanation.length() > MAX_EXPLANATION_LENGTH) {
            explanation = explanation.substring(0, MAX_EXPLANATION_LENGTH);
        }
        final double delta = entry.path("delta").asDouble(0.0);
        return new Adjustment(
                Math.max(-MAX_ADJUSTMENT, Math.min(MAX_ADJUSTMENT, delta)), explanation);
    }

    /**
     * Pulls the JSON object out of a reply.
     *
     * @param reply the reply
     * @return the extract json
     * @throws IOException if the operation fails
     */
    private String extractJson(String reply) throws IOException {
        final JsonNode root = objectMapper.readTree(reply);
        final String text = root.path("candidates").path(0)
                .path("content").path("parts").path(0).path("text").asText();

        final int start = text.indexOf('{');
        final int end = text.lastIndexOf('}');
        if (start < 0 || end <= start) {
            throw new IOException("Gemini did not return a JSON object.");
        }
        return text.substring(start, end + 1);
    }

    private String namesOf(java.util.Collection<Genre> genres) {
        return genres.stream().map(Genre::getName).collect(Collectors.joining(", "));
    }

    /**
     * Describes the candidate and the user's taste, and asks for exactly the two values the algorithm allows this step to contribute.
     *
     * @param candidate the candidate
     * @param tasteProfile the taste profile
     * @return the build prompt
     */
    private String buildPrompt(Media candidate, TasteProfile tasteProfile) {
        final String likedGenres = tasteProfile.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", "));
        final String candidateGenres = candidate.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", "));

        return "You are refining a film recommendation that has already been scored by a "
                + "deterministic formula. Do not re-rank anything.\n\n"
                + "The viewer tends to enjoy these genres: " + orNone(likedGenres) + ".\n"
                + "Candidate title: " + candidate.getTitle() + " (" + candidate.getReleaseYear() + ")\n"
                + "Candidate genres: " + orNone(candidateGenres) + "\n"
                + "Audience rating out of 10: " + candidate.getAverageRating() + "\n\n"
                + "Considering nuance the formula cannot see, such as tone or theme, reply with "
                + "strict JSON and nothing else:\n"
                + "{\"delta\": <number between -0.05 and 0.05>, "
                + "\"explanation\": \"<one short sentence saying why this suits the viewer>\"}";
    }

    private static String orNone(String value) {
        String result = value;
        if (value == null || value.isBlank()) {
            result = "no strong preference yet";
        }
        return result;
    }

    private String send(String prompt) throws IOException, InterruptedException {
        final ObjectNode part = objectMapper.createObjectNode();
        part.put("text", prompt);
        final ObjectNode content = objectMapper.createObjectNode();
        content.putArray("parts").add(part);
        final ObjectNode body = objectMapper.createObjectNode();
        body.putArray("contents").add(content);

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(ENDPOINT, model)))
                .header("Content-Type", "application/json")
                // Sent as a header rather than a query parameter so the key does
                // not end up in logs or error messages.
                .header("x-goog-api-key", apiKey)
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                .build();

        final HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Gemini returned status " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Reads the number and sentence out of the reply.
     *
     * @param reply the reply
     * @return the parse
     * @throws IOException if the operation fails
     */
    private Adjustment parse(String reply) throws IOException {
        final JsonNode parsed = objectMapper.readTree(extractJson(reply));
        final double delta = parsed.path("delta").asDouble(0.0);
        String explanation = parsed.path("explanation").asText("");

        if (explanation.length() > MAX_EXPLANATION_LENGTH) {
            explanation = explanation.substring(0, MAX_EXPLANATION_LENGTH);
        }

        // Clamped here as well, so a bad number never leaves this class even if
        // the wrapping adjuster were ever bypassed.
        final double clamped = Math.max(-MAX_ADJUSTMENT, Math.min(MAX_ADJUSTMENT, delta));
        return new Adjustment(clamped, explanation);
    }
}
