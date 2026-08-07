package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Genre;
import entity.Media;
import entity.Movie;
import entity.TVShow;
import use_case.search.MediaPage;
import use_case.search.SearchMediaDataAccess;

/**
 * Accesses TMDB search data and converts the responses into Media entities.
 * String literals defined for avoiding multiple times of using same string.
 */
public class TmdbSearchMediaDataAccess
        implements SearchMediaDataAccess {

    private static final String ID_FIELD = "id";
    private static final String RESULTS_FIELD = "results";
    private static final String GENRES_FIELD = "genres";
    private static final String VOTE_AVERAGE_FIELD = "vote_average";
    private static final String ORIGINAL_LANGUAGE_FIELD =
            "original_language";
    private static final String CREDITS_FIELD = "credits";
    private static final String CAST_FIELD = "cast";

    private final TmdbApiClient tmdbApiClient;
    private final ObjectMapper objectMapper;

    /**
     * Creates a data access object that searches TMDB through client
     * Data are translated by object mapper.
     */
    public TmdbSearchMediaDataAccess(
            TmdbApiClient tmdbApiClient) {
        this.tmdbApiClient = tmdbApiClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Searches TMDB for movies and TV shows matching a keyword.
     * Externally connected to search interactor, checking input/output and handling exceptions.
     */
    @Override
    public List<Media> search(String keyword) {
        return searchPage(keyword, 1).getMedia();
    }

    /**
     * Fetches one page of TMDB results and reports how many pages exist.
     * <p>
     * Only the page asked for is fetched. Requesting every page TMDB reports
     * meant up to 500 page requests plus a details request per result, which
     * for a common word is over ten thousand calls.
     */
    @Override
    public MediaPage searchPage(String keyword, int page) {
        final List<Media> results = new ArrayList<>();
        int totalPages = 0;
        int totalResults = 0;

        if (keyword != null && !keyword.trim().isEmpty()) {
            try {
                final JsonNode pageNode =
                        objectMapper.readTree(tmdbApiClient.searchMulti(keyword, page));
                addMediaFromPage(pageNode, results);
                totalPages = pageNode.path("total_pages").asInt();
                totalResults = pageNode.path("total_results").asInt();
            }
            catch (IOException exception) {
                throw new IllegalStateException(
                        "Unable to search media through TMDB.",
                        exception
                );
            }
        }

        return new MediaPage(results, totalPages, totalResults);
    }


    /**
     * Converts the movie and TV results (id & media type) within one search page.
     * Person results returned by multi-search are ignored since it's not included in if-else conditions.
     */
    private void addMediaFromPage(
            JsonNode pageNode,
            List<Media> results) throws IOException {
        for (JsonNode item : pageNode.path(RESULTS_FIELD)) {
            final String mediaType =
                    item.path("media_type").asText();
            final int id =
                    item.path(ID_FIELD).asInt();

            if ("movie".equals(mediaType)) {
                results.add(getMovie(id));
            }
            else if ("tv".equals(mediaType)) {
                results.add(getTvShow(id));
            }
        }
    }

    /**
     * Gets detailed movie information and converts it into a Movie.
     */
    private Movie getMovie(int movieId) throws IOException {
        final String detailsJson =
                tmdbApiClient.getMovieDetails(movieId);
        final JsonNode details =
                objectMapper.readTree(detailsJson);

        final int id =
                details.path(ID_FIELD).asInt();
        final String title =
                details.path("title").asText();
        final int releaseYear =
                parseYear(details.path("release_date").asText());
        final double averageRating =
                details.path(VOTE_AVERAGE_FIELD).asDouble();
        final List<Genre> genres =
                parseGenres(details.path(GENRES_FIELD));
        final String language =
                details.path(ORIGINAL_LANGUAGE_FIELD).asText();
        final List<String> cast =
                parseCast(
                        details.path(CREDITS_FIELD)
                                .path(CAST_FIELD)
                );
        final int runtime =
                details.path("runtime").asInt();

        return new Movie(
                id,
                title,
                releaseYear,
                averageRating,
                genres,
                language,
                cast,
                runtime
        );
    }

    /**
     * Gets complete TV-show information and converts it into a TVShow.
     */
    private TVShow getTvShow(int tvShowId) throws IOException {
        final String detailsJson =
                tmdbApiClient.getTvShowDetails(tvShowId);
        final JsonNode details =
                objectMapper.readTree(detailsJson);

        final int id =
                details.path(ID_FIELD).asInt();
        final String title =
                details.path("name").asText();
        final int releaseYear =
                parseYear(details.path("first_air_date").asText());
        final double averageRating =
                details.path(VOTE_AVERAGE_FIELD).asDouble();
        final List<Genre> genres =
                parseGenres(details.path(GENRES_FIELD));
        final String language =
                details.path(ORIGINAL_LANGUAGE_FIELD).asText();
        final List<String> cast =
                parseCast(
                        details.path(CREDITS_FIELD)
                                .path(CAST_FIELD)
                );
        final int numberOfSeasons =
                details.path("number_of_seasons").asInt();
        final int numberOfEpisodes =
                details.path("number_of_episodes").asInt();

        return new TVShow(
                id,
                title,
                releaseYear,
                averageRating,
                genres,
                language,
                cast,
                numberOfSeasons,
                numberOfEpisodes
        );
    }

    /**
     * Converts a TMDB genre array into Genre list containing both id and name of genre.
     */
    private List<Genre> parseGenres(JsonNode genreNodes) {
        final List<Genre> genres = new ArrayList<>();

        for (JsonNode genreNode : genreNodes) {
            final int id =
                    genreNode.path(ID_FIELD).asInt();
            final String name =
                    genreNode.path("name").asText();

            genres.add(new Genre(id, name));
        }

        return genres;
    }

    /**
     * Extracts only cast-member names from TMDB credits.
     */
    private List<String> parseCast(JsonNode castNodes) {
        final List<String> cast = new ArrayList<>();

        for (JsonNode castNode : castNodes) {
            cast.add(
                    castNode.path("name").asText()
            );
        }

        return cast;
    }

    /**
     * Extracts the year from a date using the YYYY-MM-DD format.
     * Lost release year is shown as 0.
     * 4 is the amount of character that represents a year.
     */
    private int parseYear(String date) {
        int year = 0;

        if (date != null && date.length() >= 4) {
            try {
                year = Integer.parseInt(
                        date.substring(0, 4)
                );
            }
            catch (NumberFormatException exception) {
                year = 0;
            }
        }

        return year;
    }
}
