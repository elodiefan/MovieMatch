package data_access;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Sends HTTP requests to the TMDB API.
 * The source link annotation regarding the API usage method is in Javadoc for reference.
 */
public class TmdbApiClient {

    private static final String BASE_URL =
            "https://api.themoviedb.org/3";

    private final HttpClient httpClient;
    private final String accessToken;

    /**
     * Creates a client for accessing the TMDB API.
     */
    public TmdbApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.accessToken = System.getenv("Tmdb_Read_Access");
    }

    /**
     * Searches for movies and TV shows from TMDB.
     * <a href="https://developer.themoviedb.org/reference/search-multi?utm_source=chatgpt.com">...</a>
     *
     * @param keyword the title entered by the user
     * @param page the result page requested from TMDB
     * @return the complete JSON response from TMDB
     * @throws IOException if the request cannot be completed
     */
    public String searchMulti(String keyword, int page)
            throws IOException {
        final String encodedKeyword = URLEncoder.encode(
                keyword.trim(),
                StandardCharsets.UTF_8
        );

        final String path =
                "/search/multi"
                        + "?query=" + encodedKeyword
                        + "&page=" + page;

        return sendGetRequest(path);
    }

    /**
     * Searches for movies only.
     *
     * Multi-search also returns people, who are dropped on the way through, so
     * a page of it can yield nothing at all and its result count bears little
     * relation to how many films and shows there are. These endpoints return
     * only what the application can actually show.
     *
     * @param keyword the keyword
     * @param page the page
     * @return the search movies
     * @throws IOException if the operation fails
     */
    public String searchMovies(String keyword, int page) throws IOException {
        return sendGetRequest("/search/movie?query=" + encode(keyword) + "&page=" + page);
    }

    /**
     * Searches for TV shows only.
     *
     * @param keyword the keyword
     * @param page the page
     * @return the search tv shows
     * @throws IOException if the operation fails
     */
    public String searchTvShows(String keyword, int page) throws IOException {
        return sendGetRequest("/search/tv?query=" + encode(keyword) + "&page=" + page);
    }

    private static String encode(String keyword) {
        return URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8);
    }

    /**
     * Finds popular movies in the given genres.
     *
     * Used to build recommendation candidates for someone whose taste profile
     * points at particular genres.
     *
     * @param genreIds the genre ids
     * @param page the page
     * @return the discover movies
     * @throws IOException if the operation fails
     */
    public String discoverMovies(String genreIds, int page) throws IOException {
        return sendGetRequest("/discover/movie?with_genres=" + genreIds
                + "&sort_by=popularity.desc&page=" + page);
    }

    /**
     * Finds popular TV shows in the given genres.
     *
     * @param genreIds the genre ids
     * @param page the page
     * @return the discover tv shows
     * @throws IOException if the operation fails
     */
    public String discoverTvShows(String genreIds, int page) throws IOException {
        return sendGetRequest("/discover/tv?with_genres=" + genreIds
                + "&sort_by=popularity.desc&page=" + page);
    }

    /**
     * Returns what is popular right now, regardless of genre.
     *
     * This is the fallback for a user with nothing in their lists yet, who has
     * no taste profile to narrow the search with.
     *
     * @param page the page
     * @return the get popular movies
     * @throws IOException if the operation fails
     */
    public String getPopularMovies(int page) throws IOException {
        return sendGetRequest("/movie/popular?page=" + page);
    }

    /**
     * Returns the TV shows that are popular right now.
     *
     * @param page the page
     * @return the get popular tv shows
     * @throws IOException if the operation fails
     */
    public String getPopularTvShows(int page) throws IOException {
        return sendGetRequest("/tv/popular?page=" + page);
    }

    /**
     * Gets the official movie genre list from TMDB.
     * ...
     *
     * @return the get movie genres
     * @throws IOException if the operation fails
     */
    public String getMovieGenres() throws IOException {
        return sendGetRequest(
                "/genre/movie/list?language=en-US"
        );
    }

    /**
     * Gets the official TV-show genre list from TMDB.
     * ...
     *
     * @return the get tv genres
     * @throws IOException if the operation fails
     */
    public String getTvGenres() throws IOException {
        return sendGetRequest(
                "/genre/tv/list?language=en-US"
        );
    }

    /**
     * Sends an authenticated GET request to TMDB.
     * As mentioned in TMDB API Reference, when response = 200, it is a seuccessful
     * response for GET endpoint.
     * ...
     *
     * @param path the path
     * @return the send get request
     * @throws IOException if the operation fails
     */
    private String sendGetRequest(String path) throws IOException {
        validateAccessToken();

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header(
                        "Authorization",
                        "Bearer " + accessToken
                )
                .header("accept", "application/json")
                .GET()
                .build();

        try {
            final HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {
                throw new IOException(
                        "TMDB request failed with status code "
                                + response.statusCode()
                );
            }

            return response.body();
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

            throw new IOException(
                    "TMDB request was interrupted.",
                    exception
            );
        }
    }

    /**
     * Checks whether the TMDB token is available.
     *
     * @throws IOException if the operation fails
     */
    private void validateAccessToken() throws IOException {
        if (accessToken == null
                || accessToken.trim().isEmpty()) {
            throw new IOException(
                    "Tmdb_Read_Access environment variable is missing."
            );
        }
    }

    /**
     * Gets complete movie details and credits from TMDB.
     *
     * @param movieId the movie id
     * @return the get movie details
     * @throws IOException if the operation fails
     */
    public String getMovieDetails(int movieId) throws IOException {
        final String path =
                "/movie/" + movieId
                        + "?append_to_response=credits";

        return sendGetRequest(path);
    }

    /**
     * Gets reviews for one movie from TMDB.
     *
     * @param movieId the movie id
     * @return the get movie reviews
     * @throws IOException if the operation fails
     */
    public String getMovieReviews(int movieId) throws IOException {
        return sendGetRequest("/movie/" + movieId + "/reviews");
    }

    /**
     * Gets complete TV-show details and credits from TMDB.
     *
     * @param tvShowId the tv show id
     * @return the get tv show details
     * @throws IOException if the operation fails
     */
    public String getTvShowDetails(int tvShowId) throws IOException {
        final String path =
                "/tv/" + tvShowId
                        + "?append_to_response=credits";

        return sendGetRequest(path);
    }

    /**
     * Gets reviews for one TV show from TMDB.
     *
     * @param tvShowId the tv show id
     * @return the get tv show reviews
     * @throws IOException if the operation fails
     */
    public String getTvShowReviews(int tvShowId) throws IOException {
        return sendGetRequest("/tv/" + tvShowId + "/reviews");
    }

    /**
     * Gets one page of movies from a TMDB endpoint.
     *
     * @param categoryPath path and query parameters after /movie or /discover
     * @return the movie-list JSON response
     * @throws IOException if the request cannot be completed
     */
    public String getMovies(String categoryPath) throws IOException {
        return sendGetRequest(categoryPath);
    }

    /**
     * Gets one page of TV shows from a TMDB endpoint.
     *
     * @param categoryPath path and query parameters after /tv or /discover
     * @return the TV-show-list JSON response
     * @throws IOException if the request cannot be completed
     */
    public String getTvShows(String categoryPath) throws IOException {
        return sendGetRequest(categoryPath);
    }
}
