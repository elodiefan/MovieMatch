package tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.TMDBAPIClient;

/**
 * Generates local movie and TV-show databases from TMDB.
 */
public class MovieDBDataGenerator {

    private static final int MOVIE_TARGET = 100;
    private static final int TV_SHOW_TARGET = 100;
    private static final int POPULAR_AMOUNT = 25;
    private static final int TOP_RATED_AMOUNT = 25;
    private static final int EARLY_AMOUNT = 20;
    private static final int NON_ENGLISH_AMOUNT = 20;
    private static final int GENRE_AMOUNT = 10;
    private static final int FIRST_PAGE = 1;
    private static final int MINIMUM_DATE_LENGTH = 4;

    private static final String RESULTS_FIELD = "results";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String NAME_FIELD = "name";
    private static final String RELEASE_YEAR_FIELD = "releaseYear";
    private static final String RELEASE_DATE_FIELD = "release_date";
    private static final String FIRST_AIR_DATE_FIELD = "first_air_date";
    private static final String AVERAGE_RATING_FIELD = "averageRating";
    private static final String VOTE_AVERAGE_FIELD = "vote_average";
    private static final String GENRES_FIELD = "genres";
    private static final String LANGUAGE_FIELD = "language";
    private static final String ORIGINAL_LANGUAGE_FIELD = "original_language";
    private static final String CREDITS_FIELD = "credits";
    private static final String CAST_FIELD = "cast";
    private static final String RUNTIME_FIELD = "runtime";
    private static final String NUMBER_OF_SEASONS_FIELD = "numberOfSeasons";
    private static final String NUMBER_OF_EPISODES_FIELD = "numberOfEpisodes";
    private static final String TMDB_NUMBER_OF_SEASONS_FIELD = "number_of_seasons";
    private static final String TMDB_NUMBER_OF_EPISODES_FIELD = "number_of_episodes";
    private static final String POPULARITY_PATH = "?sort_by=popularity.desc";
    private static final String PAGE_PATH = "&page=";
    private static final String MOVIE_PATH_PREFIX = "/discover/movie";
    private static final String TV_PATH_PREFIX = "/discover/tv";

    private static final Path MOVIES_FILE = Paths.get(
            "src", "main", "resources", "movies.json"
    );
    private static final Path TV_SHOWS_FILE = Paths.get(
            "src", "main", "resources", "tvshows.json"
    );

    private final TMDBAPIClient tmdbApiClient;
    private final ObjectMapper objectMapper;

    /**
     * Creates the database generator.
     *
     * @param tmdbApiClient client used to communicate with TMDB
     */
    public MovieDBDataGenerator(TMDBAPIClient tmdbApiClient) {
        this.tmdbApiClient = tmdbApiClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Generates local JSON files containing 200 media items.
     *
     * @param args command-line arguments
     * @throws IllegalStateException the exception thrown
     */
    public static void main(String[] args) {
        final MovieDBDataGenerator generator =
                new MovieDBDataGenerator(new TMDBAPIClient());

        try {
            generator.generateDatabases();
            System.out.println(
                    "Successfully generated movies.json and tvshows.json."
            );
        }
        catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to generate local media databases.",
                    exception
            );
        }
    }

    /**
     * Generates both local media database files.
     *
     * @throws IOException if TMDB data cannot be requested or written
     */
    public void generateDatabases() throws IOException {
        final ArrayNode movies = generateMovies();
        writeJson(MOVIES_FILE, movies);
        printGenerationResult("movies", movies, MOVIES_FILE);

        final ArrayNode tvShows = generateTvShows();
        writeJson(TV_SHOWS_FILE, tvShows);
        printGenerationResult("TV shows", tvShows, TV_SHOWS_FILE);
    }

    /**
     * Generates 100 movies from a mixture of categories.
     *
     * @return generated movie JSON objects
     * @throws IOException if TMDB cannot be accessed
     */
    private ArrayNode generateMovies() throws IOException {
        final ArrayNode movies = objectMapper.createArrayNode();
        final Set<Integer> movieIds = new HashSet<>();

        addMovies("/movie/popular?language=en-US&page=",
                POPULAR_AMOUNT, movies, movieIds);
        addMovies("/movie/top_rated?language=en-US&page=",
                TOP_RATED_AMOUNT, movies, movieIds);
        addMovies(MOVIE_PATH_PREFIX + POPULARITY_PATH
                        + "&primary_release_date.lte=1989-12-31" + PAGE_PATH,
                EARLY_AMOUNT, movies, movieIds);
        addMovies(MOVIE_PATH_PREFIX + POPULARITY_PATH
                        + "&with_original_language=fr" + PAGE_PATH,
                NON_ENGLISH_AMOUNT / 2, movies, movieIds);
        addMovies(MOVIE_PATH_PREFIX + POPULARITY_PATH
                        + "&with_original_language=ko" + PAGE_PATH,
                NON_ENGLISH_AMOUNT / 2, movies, movieIds);
        addMovies(MOVIE_PATH_PREFIX + POPULARITY_PATH
                        + "&with_genres=99" + PAGE_PATH,
                GENRE_AMOUNT / 2, movies, movieIds);
        addMovies(MOVIE_PATH_PREFIX + POPULARITY_PATH
                        + "&with_genres=16" + PAGE_PATH,
                GENRE_AMOUNT / 2, movies, movieIds);
        fillRemainingMovies(movies, movieIds);

        return movies;
    }

    /**
     * Generates 100 TV shows from a mixture of categories.
     *
     * @return generated TV-show JSON objects
     * @throws IOException if TMDB cannot be accessed
     */
    private ArrayNode generateTvShows() throws IOException {
        final ArrayNode tvShows = objectMapper.createArrayNode();
        final Set<Integer> tvShowIds = new HashSet<>();

        addTvShows("/tv/popular?language=en-US&page=",
                POPULAR_AMOUNT, tvShows, tvShowIds);
        addTvShows("/tv/top_rated?language=en-US&page=",
                TOP_RATED_AMOUNT, tvShows, tvShowIds);
        addTvShows(TV_PATH_PREFIX + POPULARITY_PATH
                        + "&first_air_date.lte=1989-12-31" + PAGE_PATH,
                EARLY_AMOUNT, tvShows, tvShowIds);
        addTvShows(TV_PATH_PREFIX + POPULARITY_PATH
                        + "&with_original_language=ja" + PAGE_PATH,
                NON_ENGLISH_AMOUNT / 2, tvShows, tvShowIds);
        addTvShows(TV_PATH_PREFIX + POPULARITY_PATH
                        + "&with_original_language=es" + PAGE_PATH,
                NON_ENGLISH_AMOUNT / 2, tvShows, tvShowIds);
        addTvShows(TV_PATH_PREFIX + POPULARITY_PATH
                        + "&with_genres=99" + PAGE_PATH,
                GENRE_AMOUNT / 2, tvShows, tvShowIds);
        addTvShows(TV_PATH_PREFIX + POPULARITY_PATH
                        + "&with_genres=10764" + PAGE_PATH,
                GENRE_AMOUNT / 2, tvShows, tvShowIds);
        fillRemainingTvShows(tvShows, tvShowIds);

        return tvShows;
    }

    /**
     * Adds unique movies from one TMDB category.
     *
     * @param pathPrefix endpoint before its page number
     * @param amount number of movies requested from this category
     * @param movies destination JSON array
     * @param movieIds IDs already added
     * @throws IOException if TMDB cannot be accessed
     */
    private void addMovies(String pathPrefix, int amount,
                           ArrayNode movies, Set<Integer> movieIds)
            throws IOException {
        int added = 0;
        int page = FIRST_PAGE;

        while (added < amount && movies.size() < MOVIE_TARGET) {
            final JsonNode results = readResults(
                    tmdbApiClient.getMovies(pathPrefix + page)
            );
            if (!results.isArray() || results.isEmpty()) {
                break;
            }

            for (JsonNode item : results) {
                if (added >= amount || movies.size() >= MOVIE_TARGET) {
                    break;
                }
                final int movieId = item.path(ID_FIELD).asInt();
                if (movieId > 0 && movieIds.add(movieId)) {
                    movies.add(createMovieNode(movieId));
                    added++;
                }
            }
            page++;
        }
    }

    /**
     * Adds unique TV shows from one TMDB category.
     *
     * @param pathPrefix endpoint before its page number
     * @param amount number of TV shows requested from this category
     * @param tvShows destination JSON array
     * @param tvShowIds IDs already added
     * @throws IOException if TMDB cannot be accessed
     */
    private void addTvShows(String pathPrefix, int amount,
                            ArrayNode tvShows, Set<Integer> tvShowIds)
            throws IOException {
        int added = 0;
        int page = FIRST_PAGE;

        while (added < amount && tvShows.size() < TV_SHOW_TARGET) {
            final JsonNode results = readResults(
                    tmdbApiClient.getTVShows(pathPrefix + page)
            );
            if (!results.isArray() || results.isEmpty()) {
                break;
            }

            for (JsonNode item : results) {
                if (added >= amount || tvShows.size() >= TV_SHOW_TARGET) {
                    break;
                }
                final int tvShowId = item.path(ID_FIELD).asInt();
                if (tvShowId > 0 && tvShowIds.add(tvShowId)) {
                    tvShows.add(createTvShowNode(tvShowId));
                    added++;
                }
            }
            page++;
        }
    }

    /**
     * Extracts the results array from a TMDB list response.
     *
     * @param response complete TMDB response
     * @return results node
     * @throws IOException if the response is not valid JSON
     */
    private JsonNode readResults(String response) throws IOException {
        return objectMapper.readTree(response).path(RESULTS_FIELD);
    }

    /**
     * Fills any remaining movie spaces after duplicate removal.
     *
     * @param movies destination movie array
     * @param movieIds IDs already added
     * @throws IOException if TMDB cannot be accessed
     */
    private void fillRemainingMovies(ArrayNode movies,
                                     Set<Integer> movieIds)
            throws IOException {
        final int remaining = MOVIE_TARGET - movies.size();
        if (remaining > 0) {
            addMovies("/discover/movie" + POPULARITY_PATH + PAGE_PATH,
                    remaining, movies, movieIds);
        }
    }

    /**
     * Fills any remaining TV-show spaces after duplicate removal.
     *
     * @param tvShows destination TV-show array
     * @param tvShowIds IDs already added
     * @throws IOException if TMDB cannot be accessed
     */
    private void fillRemainingTvShows(ArrayNode tvShows,
                                      Set<Integer> tvShowIds)
            throws IOException {
        final int remaining = TV_SHOW_TARGET - tvShows.size();
        if (remaining > 0) {
            addTvShows(TV_PATH_PREFIX + POPULARITY_PATH + PAGE_PATH,
                    remaining, tvShows, tvShowIds);
        }
    }

    /**
     * Requests full movie details and converts them to local JSON format.
     *
     * @param movieId TMDB movie ID
     * @return local movie JSON object
     * @throws IOException if movie details cannot be requested
     */
    private ObjectNode createMovieNode(int movieId) throws IOException {
        final JsonNode details = objectMapper.readTree(
                tmdbApiClient.getMovieDetails(movieId)
        );
        final ObjectNode movie = objectMapper.createObjectNode();

        movie.put(ID_FIELD, details.path(ID_FIELD).asInt());
        movie.put(TITLE_FIELD, details.path(TITLE_FIELD).asText());
        movie.put(RELEASE_YEAR_FIELD,
                parseYear(details.path(RELEASE_DATE_FIELD).asText()));
        movie.put(AVERAGE_RATING_FIELD,
                details.path(VOTE_AVERAGE_FIELD).asDouble());
        movie.set(GENRES_FIELD,
                createGenres(details.path(GENRES_FIELD)));
        movie.put(LANGUAGE_FIELD,
                details.path(ORIGINAL_LANGUAGE_FIELD).asText());
        movie.set(CAST_FIELD,
                createCast(details.path(CREDITS_FIELD).path(CAST_FIELD)));
        movie.put(RUNTIME_FIELD,
                details.path(RUNTIME_FIELD).asInt());

        return movie;
    }

    /**
     * Requests full TV-show details and converts them to local JSON format.
     *
     * @param tvShowId TMDB TV-show ID
     * @return local TV-show JSON object
     * @throws IOException if TV-show details cannot be requested
     */
    private ObjectNode createTvShowNode(int tvShowId) throws IOException {
        final JsonNode details = objectMapper.readTree(
                tmdbApiClient.getTVShowDetails(tvShowId)
        );
        final ObjectNode tvShow = objectMapper.createObjectNode();

        tvShow.put(ID_FIELD, details.path(ID_FIELD).asInt());
        tvShow.put(TITLE_FIELD, details.path(NAME_FIELD).asText());
        tvShow.put(RELEASE_YEAR_FIELD,
                parseYear(details.path(FIRST_AIR_DATE_FIELD).asText()));
        tvShow.put(AVERAGE_RATING_FIELD,
                details.path(VOTE_AVERAGE_FIELD).asDouble());
        tvShow.set(GENRES_FIELD,
                createGenres(details.path(GENRES_FIELD)));
        tvShow.put(LANGUAGE_FIELD,
                details.path(ORIGINAL_LANGUAGE_FIELD).asText());
        tvShow.set(CAST_FIELD,
                createCast(details.path(CREDITS_FIELD).path(CAST_FIELD)));
        tvShow.put(NUMBER_OF_SEASONS_FIELD,
                details.path(TMDB_NUMBER_OF_SEASONS_FIELD).asInt());
        tvShow.put(NUMBER_OF_EPISODES_FIELD,
                details.path(TMDB_NUMBER_OF_EPISODES_FIELD).asInt());

        return tvShow;
    }

    /**
     * Copies TMDB genres into the local JSON structure.
     *
     * @param genreNodes TMDB genre array
     * @return local genre array
     */
    private ArrayNode createGenres(JsonNode genreNodes) {
        final ArrayNode genres = objectMapper.createArrayNode();
        for (JsonNode genreNode : genreNodes) {
            final ObjectNode genre = objectMapper.createObjectNode();
            genre.put(ID_FIELD, genreNode.path(ID_FIELD).asInt());
            genre.put(NAME_FIELD, genreNode.path(NAME_FIELD).asText());
            genres.add(genre);
        }
        return genres;
    }

    /**
     * Copies all cast-member names into the local JSON structure.
     *
     * @param castNodes TMDB cast array
     * @return array containing all cast names
     */
    private ArrayNode createCast(JsonNode castNodes) {
        final ArrayNode cast = objectMapper.createArrayNode();
        for (JsonNode castNode : castNodes) {
            final String name = castNode.path(NAME_FIELD).asText();
            if (!name.isEmpty()) {
                cast.add(name);
            }
        }
        return cast;
    }

    /**
     * Extracts a release year from a TMDB date.
     *
     * @param date date in YYYY-MM-DD format
     * @return release year, or zero when the year is unavailable
     */
    private int parseYear(String date) {
        int year = 0;
        if (date != null && date.length() >= MINIMUM_DATE_LENGTH) {
            try {
                year = Integer.parseInt(
                        date.substring(0, MINIMUM_DATE_LENGTH)
                );
            }
            catch (NumberFormatException exception) {
                year = 0;
            }
        }
        return year;
    }

    /**
     * Writes a JSON array to a resource file.
     *
     * @param file destination file
     * @param content JSON array to write
     * @throws IOException if the file cannot be written
     */
    private void writeJson(Path file, ArrayNode content) throws IOException {
        final Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(file.toFile(), content);
    }

    /**
     * Prints the number and location of generated records.
     *
     * @param label type of records generated
     * @param content generated records
     * @param file output file
     */
    private void printGenerationResult(String label, ArrayNode content,
                                       Path file) {
        System.out.println(
                "Generated " + content.size() + " " + label + " at "
                        + file.toAbsolutePath()
        );
    }
}
