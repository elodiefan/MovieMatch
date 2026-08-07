package data_access;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/** Sends HTTP requests to the TMDB API. */
public class TmdbApiClient {

    private static final String BASE_URL =
            "https://api.themoviedb.org/3";

    private final HttpClient httpClient;
    private final String accessToken;

    /** Creates a client for accessing the TMDB API. */
    public TmdbApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.accessToken = System.getenv("Tmdb_Read_Access");
    }

    /** Searches for movies and TV shows from TMDB. */
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

    /** Searches for movies only. */
    public String searchMovies(String keyword, int page) throws IOException {
        return sendGetRequest("/search/movie?query=" + encode(keyword) + "&page=" + page);
    }

    /** Searches for TV shows only. */
    public String searchTvShows(String keyword, int page) throws IOException {
        return sendGetRequest("/search/tv?query=" + encode(keyword) + "&page=" + page);
    }

    private static String encode(String keyword) {
        return URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8);
    }

    /** Finds popular movies in the given genres. */
    public String discoverMovies(String genreIds, int page) throws IOException {
        return sendGetRequest("/discover/movie?with_genres=" + genreIds
                + "&sort_by=popularity.desc&page=" + page);
    }

    /** Finds popular TV shows in the given genres. */
    public String discoverTvShows(String genreIds, int page) throws IOException {
        return sendGetRequest("/discover/tv?with_genres=" + genreIds
                + "&sort_by=popularity.desc&page=" + page);
    }

    /** Returns what is popular right now, regardless of genre. */
    public String getPopularMovies(int page) throws IOException {
        return sendGetRequest("/movie/popular?page=" + page);
    }

    /** Returns the TV shows that are popular right now. */
    public String getPopularTvShows(int page) throws IOException {
        return sendGetRequest("/tv/popular?page=" + page);
    }

    /** Gets the official movie genre list from TMDB. */
    public String getMovieGenres() throws IOException {
        return sendGetRequest(
                "/genre/movie/list?language=en-US"
        );
    }

    /** Gets the official TV-show genre list from TMDB. */
    public String getTvGenres() throws IOException {
        return sendGetRequest(
                "/genre/tv/list?language=en-US"
        );
    }

    /** Sends an authenticated GET request to TMDB. */
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

    /** Checks whether the TMDB token is available. */
    private void validateAccessToken() throws IOException {
        if (accessToken == null
                || accessToken.trim().isEmpty()) {
            throw new IOException(
                    "Tmdb_Read_Access environment variable is missing."
            );
        }
    }

    /** Gets complete movie details and credits from TMDB. */
    public String getMovieDetails(int movieId) throws IOException {
        final String path =
                "/movie/" + movieId
                        + "?append_to_response=credits";

        return sendGetRequest(path);
    }

    /** Gets reviews for one movie from TMDB. */
    public String getMovieReviews(int movieId) throws IOException {
        return sendGetRequest("/movie/" + movieId + "/reviews");
    }

    /** Gets complete TV-show details and credits from TMDB. */
    public String getTvShowDetails(int tvShowId) throws IOException {
        final String path =
                "/tv/" + tvShowId
                        + "?append_to_response=credits";

        return sendGetRequest(path);
    }

    /** Gets reviews for one TV show from TMDB. */
    public String getTvShowReviews(int tvShowId) throws IOException {
        return sendGetRequest("/tv/" + tvShowId + "/reviews");
    }
}
