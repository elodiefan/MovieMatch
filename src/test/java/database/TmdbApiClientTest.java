package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TmdbApiClientTest {

    @Test
    void successfulSearchReturnsBodyAndBuildsAuthenticatedRequest()
            throws IOException {
        final FakeRequestSender sender = new FakeRequestSender();
        sender.body = "{\"results\":[]}";
        final TmdbApiClient client = new TmdbApiClient(sender, "test-token");

        final String result = client.searchMovies("  star wars/hope  ", 2);

        assertEquals("{\"results\":[]}", result);
        assertEquals("https://api.themoviedb.org/3/search/movie"
                        + "?query=star+wars%2Fhope&page=2",
                sender.request.uri().toString());
        assertEquals("Bearer test-token", sender.request.headers()
                .firstValue("Authorization").orElseThrow());
        assertEquals("application/json", sender.request.headers()
                .firstValue("accept").orElseThrow());
        assertEquals("GET", sender.request.method());
    }

    @Test
    void nullAndBlankTokensFailBeforeSendingARequest() {
        final FakeRequestSender nullTokenSender = new FakeRequestSender();
        final FakeRequestSender blankTokenSender = new FakeRequestSender();

        final IOException nullException = assertThrows(IOException.class,
                () -> new TmdbApiClient(nullTokenSender, null)
                        .searchMovies("inception", 1));
        final IOException blankException = assertThrows(IOException.class,
                () -> new TmdbApiClient(blankTokenSender, "  ")
                        .searchMovies("inception", 1));

        assertEquals("Tmdb_Read_Access environment variable is missing.",
                nullException.getMessage());
        assertEquals("Tmdb_Read_Access environment variable is missing.",
                blankException.getMessage());
        assertFalse(nullTokenSender.called);
        assertFalse(blankTokenSender.called);
    }

    @Test
    void non200ResponseBecomesIOException() {
        final FakeRequestSender sender = new FakeRequestSender();
        sender.statusCode = 503;
        final TmdbApiClient client = new TmdbApiClient(sender, "token");

        final IOException exception = assertThrows(IOException.class,
                () -> client.getPopularMovies(4));

        assertEquals("TMDB request failed with status code 503",
                exception.getMessage());
    }

    @Test
    void interruptedRequestRestoresFlagAndBecomesIOException() {
        final FakeRequestSender sender = new FakeRequestSender();
        sender.interrupted = true;
        final TmdbApiClient client = new TmdbApiClient(sender, "token");

        final IOException exception = assertThrows(IOException.class,
                () -> client.getPopularTvShows(3));

        assertEquals("TMDB request was interrupted.", exception.getMessage());
        assertTrue(Thread.currentThread().isInterrupted());
        assertSame(sender.interruption, exception.getCause());
    }

    @AfterEach
    void clearInterruptedFlag() {
        Thread.interrupted();
    }

    @Test
    void everyEndpointBuildsTheExpectedPath() throws IOException {
        final FakeRequestSender sender = new FakeRequestSender();
        final TmdbApiClient client = new TmdbApiClient(sender, "token");

        client.searchMulti("star wars", 2);
        assertPath(sender, "/search/multi?query=star+wars&page=2");

        client.searchTvShows("the bear", 3);
        assertPath(sender, "/search/tv?query=the+bear&page=3");

        client.discoverMovies("18,35", 4);
        assertPath(sender, "/discover/movie?with_genres=18,35"
                + "&sort_by=popularity.desc&page=4");

        client.discoverTvShows("99", 5);
        assertPath(sender, "/discover/tv?with_genres=99"
                + "&sort_by=popularity.desc&page=5");

        client.getMovieGenres();
        assertPath(sender, "/genre/movie/list?language=en-US");

        client.getTvGenres();
        assertPath(sender, "/genre/tv/list?language=en-US");

        client.getMovieDetails(101);
        assertPath(sender, "/movie/101?append_to_response=credits");

        client.getMovieReviews(101);
        assertPath(sender, "/movie/101/reviews");

        client.getTvShowDetails(202);
        assertPath(sender, "/tv/202?append_to_response=credits");

        client.getTvShowReviews(202);
        assertPath(sender, "/tv/202/reviews");

        client.getMovies("/movie/now_playing?page=6");
        assertPath(sender, "/movie/now_playing?page=6");

        client.getTvShows("/tv/top_rated?page=7");
        assertPath(sender, "/tv/top_rated?page=7");
    }

    private static void assertPath(FakeRequestSender sender, String path) {
        assertEquals("https://api.themoviedb.org/3" + path,
                sender.request.uri().toString());
    }

    private static class FakeRequestSender
            implements TmdbApiClient.RequestSender {
        private int statusCode = 200;
        private String body = "{}";
        private boolean interrupted;
        private boolean called;
        private HttpRequest request;
        private final InterruptedException interruption =
                new InterruptedException("controlled interruption");

        @Override
        public TmdbApiClient.Response send(HttpRequest sentRequest)
                throws InterruptedException {
            called = true;
            request = sentRequest;
            if (interrupted) {
                throw interruption;
            }
            return new TmdbApiClient.Response(statusCode, body);
        }
    }
}
