package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Genre;
import entity.Media;
import entity.Movie;
import entity.TVShow;
import use_case.recommendation.MediaCatalogueDataAccessInterface;

/**
 * Supplies recommendation candidates from TMDB.
 */
public class TmdbMediaCatalogue implements MediaCatalogueDataAccessInterface {

    /**
     * How many pages of candidates to gather.
     */
    private static final int CANDIDATE_PAGES = 1;

    private static final String RESULTS_FIELD = "results";
    private static final String ID_FIELD = "id";
    private static final String GENRE_IDS_FIELD = "genre_ids";
    private static final String VOTE_AVERAGE_FIELD = "vote_average";
    private static final String ORIGINAL_LANGUAGE_FIELD = "original_language";

    private final TmdbApiClient tmdbApiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Genre id to name, fetched once because it never changes during a run.
     */
    private Map<Integer, String> genreNames;

    public TmdbMediaCatalogue(TmdbApiClient tmdbApiClient) {
        this.tmdbApiClient = tmdbApiClient;
    }

    @Override
    public List<Media> findCandidates(Set<Genre> genres, Set<Integer> excludeMediaIds,
                                      boolean allowAdultContent) {
        final List<Media> candidates = new ArrayList<>();

        try {
            for (int page = 1; page <= CANDIDATE_PAGES; page++) {
                if (genres == null || genres.isEmpty()) {
                    // No taste profile to narrow with, so show what is popular.
                    collect(tmdbApiClient.discoverPopularMovies(page, allowAdultContent),
                            candidates, true);
                    collect(tmdbApiClient.discoverPopularTvShows(page, allowAdultContent),
                            candidates, false);
                }
                else {
                    // TMDB reads a bar between ids as "any of these", but a raw
                    // bar is not legal in a URL, so it goes in already encoded.
                    final String ids = genres.stream()
                            .map(genre -> String.valueOf(genre.getId()))
                            .collect(Collectors.joining("%7C"));
                    collect(tmdbApiClient.discoverMovies(ids, page, allowAdultContent),
                            candidates, true);
                    collect(tmdbApiClient.discoverTvShows(ids, page, allowAdultContent),
                            candidates, false);
                }
            }
        }
        catch (IOException exception) {
            throw new IllegalStateException("Unable to load recommendations from TMDB.", exception);
        }

        return withoutExcluded(candidates, excludeMediaIds);
    }

    @Override
    public Media findById(int mediaId) {
        Media result = null;
        try {
            final JsonNode details =
                    objectMapper.readTree(tmdbApiClient.getMovieDetails(mediaId));
            result = toMovie(details);
        }
        catch (IOException exception) {
            // A title the catalogue does not have is not an error; the interface
            // says to return null and let the caller decide what that means.
            result = null;
        }
        return result;
    }

    /**
     * Drops anything the user has already seen or rated.
     *
     * @param candidates the candidates
     * @param excludeMediaIds the exclude media ids
     * @return the without excluded
     */
    private List<Media> withoutExcluded(List<Media> candidates, Set<Integer> excludeMediaIds) {
        final List<Media> kept = new ArrayList<>();
        for (Media media : candidates) {
            if (excludeMediaIds == null || !excludeMediaIds.contains(media.getID())) {
                kept.add(media);
            }
        }
        return kept;
    }

    private void collect(String json, List<Media> into, boolean movies) throws IOException {
        final JsonNode page = objectMapper.readTree(json);
        for (JsonNode item : page.path(RESULTS_FIELD)) {
            if (movies) {
                into.add(toMovieSummary(item));
            }
            else {
                into.add(toTvSummary(item));
            }
        }
    }

    /**
     * Builds a Movie from a listing entry.
     *
     * @param item the item
     * @return the to movie summary
     */
    private Movie toMovieSummary(JsonNode item) {
        return new Movie(
                item.path(ID_FIELD).asInt(),
                item.path("title").asText(),
                parseYear(item.path("release_date").asText()),
                item.path(VOTE_AVERAGE_FIELD).asDouble(),
                toGenres(item.path(GENRE_IDS_FIELD)),
                item.path(ORIGINAL_LANGUAGE_FIELD).asText(),
                new ArrayList<>(),
                0,
                item.path("overview").asText(""),
                item.path("poster_path").asText(""));
    }

    private TVShow toTvSummary(JsonNode item) {
        return new TVShow(
                item.path(ID_FIELD).asInt(),
                item.path("name").asText(),
                parseYear(item.path("first_air_date").asText()),
                item.path(VOTE_AVERAGE_FIELD).asDouble(),
                toGenres(item.path(GENRE_IDS_FIELD)),
                item.path(ORIGINAL_LANGUAGE_FIELD).asText(),
                new ArrayList<>(),
                0,
                0,
                item.path("overview").asText(""),
                item.path("poster_path").asText(""));
    }

    private Movie toMovie(JsonNode details) {
        final List<Genre> genres = new ArrayList<>();
        for (JsonNode genre : details.path("genres")) {
            genres.add(new Genre(genre.path(ID_FIELD).asInt(), genre.path("name").asText()));
        }
        return new Movie(
                details.path(ID_FIELD).asInt(),
                details.path("title").asText(),
                parseYear(details.path("release_date").asText()),
                details.path(VOTE_AVERAGE_FIELD).asDouble(),
                genres,
                details.path(ORIGINAL_LANGUAGE_FIELD).asText(),
                new ArrayList<>(),
                details.path("runtime").asInt(),
                details.path("overview").asText(""),
                details.path("poster_path").asText(""));
    }

    /**
     * Turns TMDB's genre ids into genres, naming them where possible.
     *
     * @param genreIds the genre ids
     * @return the to genres
     */
    private List<Genre> toGenres(JsonNode genreIds) {
        final Map<Integer, String> names = genreNames();
        final List<Genre> genres = new ArrayList<>();
        for (JsonNode id : genreIds) {
            final int genreId = id.asInt();
            genres.add(new Genre(genreId, names.getOrDefault(genreId, "Unknown")));
        }
        return genres;
    }

    /**
     * Loads the genre names once per run.
     *
     * @return the genre names
     */
    private Map<Integer, String> genreNames() {
        if (genreNames == null) {
            genreNames = new HashMap<>();
            try {
                addGenreNames(tmdbApiClient.getMovieGenres());
                addGenreNames(tmdbApiClient.getTvGenres());
            }
            catch (IOException exception) {
                // Names are only for display; ids still drive the scoring, so a
                // failure here should not stop recommendations appearing.
                genreNames = new HashMap<>();
            }
        }
        return genreNames;
    }

    private void addGenreNames(String json) throws IOException {
        for (JsonNode genre : objectMapper.readTree(json).path("genres")) {
            genreNames.put(genre.path(ID_FIELD).asInt(), genre.path("name").asText());
        }
    }

    private int parseYear(String date) {
        int year = 0;
        if (date != null && date.length() >= 4) {
            try {
                year = Integer.parseInt(date.substring(0, 4));
            }
            catch (NumberFormatException exception) {
                year = 0;
            }
        }
        return year;
    }

    /**
     * Returns the distinct genres across some titles, used to build a taste profile from what a user has already engaged with.
     *
     * @param media the media
     * @return the genres of
     */
    public Set<Genre> genresOf(List<Media> media) {
        final Set<Genre> genres = new LinkedHashSet<>();
        for (Media item : media) {
            genres.addAll(item.getGenres());
        }
        return genres;
    }
}
