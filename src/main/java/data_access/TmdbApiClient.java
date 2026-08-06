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
     * Gets the official movie genre list from TMDB.
     * <a href="https://developer.themoviedb.org/reference/genre-movie-list?utm_source=chatgpt.com">...</a>
     *
     * @return the complete genre JSON response from TMDB
     * @throws IOException if the request cannot be completed
     */
    public String getMovieGenres() throws IOException {
        return sendGetRequest(
                "/genre/movie/list?language=en-US"
        );
    }

    /**
     * Gets the official TV-show genre list from TMDB.
     * <a href="https://developer.themoviedb.org/reference/genre-tv-list?utm_source=chatgpt.com">...</a>
     *
     * @return the complete genre JSON response from TMDB
     * @throws IOException if the request cannot be completed
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
     *  <a href="https://developer.themoviedb.org/docs/authentication-application?utm_source=chatgpt.com">...</a>
     * @param path API path and query parameters
     * @return the response body supplied by TMDB
     * @throws IOException if authentication or the request fails
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
     * @throws IOException if the token is missing
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
     * <a href="https://developer.themoviedb.org/reference/movie-details?utm_source=chatgpt.com"></a>
     *
     * @param movieId the movie ID supplied by TMDB
     * @return the complete movie details JSON response
     * @throws IOException if the request cannot be completed
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
     * @param movieId the movie ID supplied by TMDB
     * @return the complete movie reviews JSON response
     * @throws IOException if the request cannot be completed
     */
    public String getMovieReviews(int movieId) throws IOException {
        return sendGetRequest("/movie/" + movieId + "/reviews");
    }

    /**
     * Gets complete TV-show details and credits from TMDB.
     *
     * @param tvShowId the TV-show ID supplied by TMDB
     * @return the complete TV-show details JSON response
     * @throws IOException if the request cannot be completed
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
     * @param tvShowId the TV-show ID supplied by TMDB
     * @return the complete TV-show reviews JSON response
     * @throws IOException if the request cannot be completed
     */
    public String getTvShowReviews(int tvShowId) throws IOException {
        return sendGetRequest("/tv/" + tvShowId + "/reviews");
    }
}
