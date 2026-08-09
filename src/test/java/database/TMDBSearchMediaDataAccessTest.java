package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import entity.Movie;
import entity.TVShow;
import use_case.search.MediaPage;

class TMDBSearchMediaDataAccessTest {

    @Test
    void convertsMovieAndTvDetailsAndCombinesPageMetadata() {
        final RecordingTMDBAPIClient client = new RecordingTMDBAPIClient();
        final TMDBSearchMediaDataAccess dataAccess =
                new TMDBSearchMediaDataAccess(client);

        final MediaPage page = dataAccess.searchPage("space", 3);

        assertEquals("space", client.keyword);
        assertEquals(3, client.page);
        assertEquals(2, page.getMedia().size());
        assertEquals(5, page.getTotalPages());
        assertEquals(13, page.getTotalResults());

        final Movie movie = assertInstanceOf(Movie.class, page.getMedia().get(0));
        assertEquals(101, movie.getID());
        assertEquals("Test Movie", movie.getTitle());
        assertEquals(2024, movie.getReleaseYear());
        assertEquals(123, movie.getRuntime());
        assertEquals("Movie overview", movie.getOverview());
        assertEquals("/movie.jpg", movie.getPosterPath());
        assertEquals("Actor One", movie.getCast().get(0));
        assertEquals("Drama", movie.getGenres().get(0).getName());

        final TVShow show = assertInstanceOf(TVShow.class, page.getMedia().get(1));
        assertEquals(2020, show.getReleaseYear());
        assertEquals(4, show.numberOfSeasons());
        assertEquals(32, show.numberOfEpisodes());
    }

    @Test
    void searchDelegatesToTheFirstPage() {
        final RecordingTMDBAPIClient client = new RecordingTMDBAPIClient();

        assertEquals(2, new TMDBSearchMediaDataAccess(client).search("space").size());
        assertEquals(1, client.page);
    }

    @Test
    void blankKeywordDoesNotCallTMDB() {
        final RecordingTMDBAPIClient client = new RecordingTMDBAPIClient();

        final MediaPage page = new TMDBSearchMediaDataAccess(client).searchPage("  ", 1);

        assertTrue(page.getMedia().isEmpty());
        assertEquals(0, page.getTotalPages());
        assertEquals(0, client.calls);
    }

    @Test
    void invalidOrMissingDatesBecomeYearZero() {
        final RecordingTMDBAPIClient client = new RecordingTMDBAPIClient();
        client.movieDate = "unknown";
        client.showDate = "";

        final MediaPage page = new TMDBSearchMediaDataAccess(client).searchPage("x", 1);

        assertEquals(0, page.getMedia().get(0).getReleaseYear());
        assertEquals(0, page.getMedia().get(1).getReleaseYear());
    }

    @Test
    void ioFailureIsTranslatedForTheFallbackLayer() {
        final RecordingTMDBAPIClient client = new RecordingTMDBAPIClient();
        client.fail = true;

        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new TMDBSearchMediaDataAccess(client).search("space"));

        assertInstanceOf(IOException.class, exception.getCause());
    }

    private static class RecordingTMDBAPIClient extends TMDBAPIClient {
        private String keyword;
        private int page;
        private int calls;
        private boolean fail;
        private String movieDate = "2024-05-01";
        private String showDate = "2020-01-02";

        @Override
        public String searchMovies(String requestedKeyword, int requestedPage)
                throws IOException {
            record(requestedKeyword, requestedPage);
            return "{\"results\":[{\"id\":101}],\"total_pages\":2,\"total_results\":7}";
        }

        @Override
        public String searchTVShows(String requestedKeyword, int requestedPage)
                throws IOException {
            record(requestedKeyword, requestedPage);
            return "{\"results\":[{\"id\":202}],\"total_pages\":5,\"total_results\":6}";
        }

        @Override
        public String getMovieDetails(int movieId) {
            return "{\"id\":101,\"title\":\"Test Movie\",\"release_date\":\""
                    + movieDate + "\",\"vote_average\":8.2,\"genres\":[{\"id\":18,"
                    + "\"name\":\"Drama\"}],\"original_language\":\"en\","
                    + "\"credits\":{\"cast\":[{\"name\":\"Actor One\"}]},"
                    + "\"runtime\":123,\"overview\":\"Movie overview\","
                    + "\"poster_path\":\"/movie.jpg\"}";
        }

        @Override
        public String getTVShowDetails(int tvShowId) {
            return "{\"id\":202,\"name\":\"Test Show\",\"first_air_date\":\""
                    + showDate + "\",\"vote_average\":7.4,\"genres\":[],"
                    + "\"original_language\":\"fr\",\"credits\":{\"cast\":[]},"
                    + "\"number_of_seasons\":4,\"number_of_episodes\":32}";
        }

        private void record(String requestedKeyword, int requestedPage) throws IOException {
            if (fail) {
                throw new IOException("offline");
            }
            keyword = requestedKeyword;
            page = requestedPage;
            calls++;
        }
    }
}
