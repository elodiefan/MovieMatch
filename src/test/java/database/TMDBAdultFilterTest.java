package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests that adult titles are kept out at the request rather than afterwards.
 *
 * TMDB only sets its adult flag for material catalogued as pornography and
 * leaves it false on plenty that is plainly not for a general audience, so
 * filtering on that flag alone changes nothing. Excluding the keywords is what
 * actually works, and these tests pin that to the URL that gets sent.
 */
class TMDBAdultFilterTest {

    /** hentai, ecchi, softcore, erotic, animated porn, pornography. */
    private static final String BLOCKED_IDS =
            "198385%7C195669%7C155477%7C256466%7C378816%7C445";

    /**
     * Captures the request instead of sending it.
     */
    private static class CapturingSender implements TMDBAPIClient.RequestSender {
        private HttpRequest request;

        @Override
        public TMDBAPIClient.Response send(HttpRequest sentRequest) {
            this.request = sentRequest;
            return new TMDBAPIClient.Response(200, "{\"results\":[]}");
        }

        private String uri() {
            return request.uri().toString();
        }
    }

    private CapturingSender sender;
    private TMDBAPIClient client;

    private void given() {
        sender = new CapturingSender();
        client = new TMDBAPIClient(sender, "test-token");
    }

    @Test
    @DisplayName("discovering movies with the setting off excludes the adult keywords")
    void movieDiscoveryFiltersWhenNotAllowed() throws IOException {
        given();

        client.discoverMovies("16", 1, false);

        assertTrue(sender.uri().contains("without_keywords=" + BLOCKED_IDS),
                "the request must exclude the keywords, since the adult flag alone misses these");
        assertTrue(sender.uri().contains("include_adult=false"));
        assertTrue(sender.uri().startsWith("https://api.themoviedb.org/3/discover/movie"));
    }

    @Test
    @DisplayName("discovering shows with the setting off excludes the adult keywords")
    void TVDiscoveryFiltersWhenNotAllowed() throws IOException {
        given();

        client.discoverTVShows("16", 1, false);

        assertTrue(sender.uri().contains("without_keywords=" + BLOCKED_IDS));
        assertTrue(sender.uri().contains("include_adult=false"));
        assertTrue(sender.uri().startsWith("https://api.themoviedb.org/3/discover/tv"));
    }

    @Test
    @DisplayName("turning the setting on stops filtering, rather than filtering differently")
    void nothingIsExcludedWhenAllowed() throws IOException {
        given();
        client.discoverMovies("16", 1, true);
        assertFalse(sender.uri().contains("without_keywords"));
        assertFalse(sender.uri().contains("include_adult"));

        given();
        client.discoverTVShows("16", 1, true);
        assertFalse(sender.uri().contains("without_keywords"));
    }

    @Test
    @DisplayName("the popular fallback is filtered too, not just genre discovery")
    void popularIsFilteredAsWell() throws IOException {
        // A user with nothing in their lists gets the popular fallback, and that
        // is exactly where the complaint came from, so it has to filter as well.
        given();
        client.discoverPopularMovies(1, false);
        assertTrue(sender.uri().contains("without_keywords=" + BLOCKED_IDS));
        assertTrue(sender.uri().contains("sort_by=popularity.desc"));

        given();
        client.discoverPopularTVShows(1, false);
        assertTrue(sender.uri().contains("without_keywords=" + BLOCKED_IDS));
        assertTrue(sender.uri().contains("sort_by=popularity.desc"));
    }

    @Test
    @DisplayName("the popular fallback goes through discover, which is what can filter")
    void popularUsesDiscoverBecausePlainPopularCannotFilter() throws IOException {
        // /movie/popular and /tv/popular accept no filters at all, so asking
        // them would quietly return adult titles however the setting is set.
        given();
        client.discoverPopularMovies(2, false);
        assertTrue(sender.uri().startsWith("https://api.themoviedb.org/3/discover/movie"),
                "the plain popular endpoint cannot be filtered, so discover is used");

        given();
        client.discoverPopularTVShows(2, false);
        assertTrue(sender.uri().startsWith("https://api.themoviedb.org/3/discover/tv"));
    }

    @Test
    @DisplayName("the genre and page asked for still reach the request")
    void filteringDoesNotDisturbTheRestOfTheQuery() throws IOException {
        given();

        client.discoverMovies("28%7C35", 4, false);

        assertTrue(sender.uri().contains("with_genres=28%7C35"));
        assertTrue(sender.uri().contains("page=4"));
        assertTrue(sender.uri().contains("sort_by=popularity.desc"));
    }

    @Test
    @DisplayName("the unfiltered two-argument call is unchanged")
    void twoArgumentCallStillBuildsTheOriginalPath() throws IOException {
        // Search already relies on these, so adding the filter must not have
        // altered the paths they were producing before.
        given();
        client.discoverMovies("18,35", 4);
        assertEquals("https://api.themoviedb.org/3/discover/movie"
                + "?with_genres=18,35&sort_by=popularity.desc&page=4", sender.uri());

        given();
        client.discoverTVShows("99", 5);
        assertEquals("https://api.themoviedb.org/3/discover/tv"
                + "?with_genres=99&sort_by=popularity.desc&page=5", sender.uri());
    }
}
