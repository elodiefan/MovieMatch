package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Media;
import entity.Movie;
import entity.TVShow;
import use_case.search.MediaPage;
import use_case.search.SearchMediaDataAccess;

class FallbackSearchMediaDataAccessTest {

    private static Movie movie(int id, String title) {
        return new Movie(id, title, 2000, 7.0, new ArrayList<>(),
                "en", new ArrayList<>(), 100);
    }

    private static TVShow show(int id, String title) {
        return new TVShow(id, title, 2010, 8.0, new ArrayList<>(),
                "en", new ArrayList<>(), 2, 12);
    }

    @Test
    void successfulPrimarySearchIsReturnedWithoutUsingFallback() {
        final Movie primaryMovie = movie(9, "Remote");
        final SearchMediaDataAccess primary = new StubSearchDataAccess(
                List.of(primaryMovie), new MediaPage(List.of(primaryMovie), 4, 40), false);
        final FallbackSearchMediaDataAccess dataAccess = create(primary);

        assertSame(primaryMovie, dataAccess.search("anything").get(0));
        assertEquals(4, dataAccess.searchPage("anything", 2).getTotalPages());
    }

    @Test
    void primaryFailureCombinesMovieAndTvFallbackResults() {
        final FallbackSearchMediaDataAccess dataAccess = create(
                new StubSearchDataAccess(List.of(), null, true));

        final List<Media> results = dataAccess.search("star");

        assertEquals(List.of(1, 2), List.of(
                results.get(0).getID(), results.get(1).getID()));
    }

    @Test
    void failedPagedSearchUsesOnlyOneLocalPage() {
        final FallbackSearchMediaDataAccess dataAccess = create(
                new StubSearchDataAccess(List.of(), null, true));

        final MediaPage first = dataAccess.searchPage("star", 1);
        final MediaPage later = dataAccess.searchPage("star", 2);

        assertEquals(2, first.getMedia().size());
        assertEquals(1, first.getTotalPages());
        assertEquals(2, first.getTotalResults());
        assertTrue(later.getMedia().isEmpty());
    }

    private static FallbackSearchMediaDataAccess create(SearchMediaDataAccess primary) {
        return new FallbackSearchMediaDataAccess(primary,
                new LocalMovieDatabase(List.of(movie(1, "Star Wars"))),
                new LocalTVShowDatabase(List.of(show(2, "Star Trek"))));
    }

    private static class StubSearchDataAccess implements SearchMediaDataAccess {
        private final List<Media> searchResult;
        private final MediaPage pageResult;
        private final boolean fails;

        StubSearchDataAccess(List<Media> searchResult, MediaPage pageResult, boolean fails) {
            this.searchResult = searchResult;
            this.pageResult = pageResult;
            this.fails = fails;
        }

        @Override
        public List<Media> search(String keyword) {
            failIfRequested();
            return searchResult;
        }

        @Override
        public MediaPage searchPage(String keyword, int page) {
            failIfRequested();
            return pageResult;
        }

        private void failIfRequested() {
            if (fails) {
                throw new IllegalStateException("TMDB unavailable");
            }
        }
    }
}
