package use_case.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Media;
import entity.Movie;

/**
 * Tests for the Search Interactor, and in particular for how much it fetches.
 * <p>
 * A search used to ask the source for every page it reported, which for a
 * common keyword meant ten thousand results and roughly the same number of
 * network calls. These pin the paging behaviour that replaced it.
 */
class SearchInteractorTest {

    /** Records which pages were asked for and hands back fabricated results. */
    private static class FakePagedDataAccess implements SearchMediaDataAccess {
        private final int totalPages;
        private final int perPage;
        private final List<Integer> pagesRequested = new ArrayList<>();

        FakePagedDataAccess(int totalPages, int perPage) {
            this.totalPages = totalPages;
            this.perPage = perPage;
        }

        @Override
        public List<Media> search(String keyword) {
            return searchPage(keyword, 1).getMedia();
        }

        @Override
        public MediaPage searchPage(String keyword, int page) {
            pagesRequested.add(page);
            final List<Media> media = new ArrayList<>();
            for (int i = 0; i < perPage; i++) {
                media.add(new Movie(page * 100 + i, "page " + page + " item " + i,
                        2000, 7.5, new ArrayList<>(), "en", new ArrayList<>(), 120));
            }
            return new MediaPage(media, totalPages, totalPages * perPage);
        }
    }

    /** Captures whatever the interactor reports. */
    private static class RecordingPresenter implements SearchOutputBoundary {
        private SearchOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(SearchOutputData outputData) {
            this.success = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            this.failure = error;
        }
    }

    @Test
    void aSearchFetchesOnlyOneBlockOfPages() {
        final FakePagedDataAccess dataAccess = new FakePagedDataAccess(500, 20);
        final RecordingPresenter presenter = new RecordingPresenter();

        new SearchInteractor(dataAccess, presenter).execute(new SearchInputData("hi"));

        assertEquals(SearchInteractor.PAGES_PER_REQUEST, dataAccess.pagesRequested.size(),
                "a source reporting 500 pages must not be asked for all of them");
        assertEquals(List.of(1, 2, 3), dataAccess.pagesRequested);
        assertEquals(60, presenter.success.getResults().size());
    }

    @Test
    void moreIsOfferedWhenPagesRemain() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchInteractor(new FakePagedDataAccess(500, 20), presenter)
                .execute(new SearchInputData("hi"));

        assertTrue(presenter.success.isMoreAvailable());
        assertEquals(4, presenter.success.getNextPage(), "the next block starts after this one");
        assertFalse(presenter.success.isAppending(), "a fresh search replaces what is on screen");
    }

    @Test
    void moreIsNotOfferedWhenEverythingFits() {
        final FakePagedDataAccess dataAccess = new FakePagedDataAccess(1, 13);
        final RecordingPresenter presenter = new RecordingPresenter();

        new SearchInteractor(dataAccess, presenter).execute(new SearchInputData("inception"));

        assertEquals(1, dataAccess.pagesRequested.size(), "one page exists, so ask once");
        assertFalse(presenter.success.isMoreAvailable());
        assertEquals(13, presenter.success.getResults().size());
    }

    @Test
    void aShortResultSetStopsEarlyRatherThanRequestingMissingPages() {
        final FakePagedDataAccess dataAccess = new FakePagedDataAccess(2, 20);
        final RecordingPresenter presenter = new RecordingPresenter();

        new SearchInteractor(dataAccess, presenter).execute(new SearchInputData("hi"));

        assertEquals(List.of(1, 2), dataAccess.pagesRequested,
                "page 3 does not exist and must not be requested");
        assertFalse(presenter.success.isMoreAvailable());
    }

    @Test
    void loadMoreContinuesFromWhereTheLastBlockStopped() {
        final FakePagedDataAccess dataAccess = new FakePagedDataAccess(500, 20);
        final RecordingPresenter presenter = new RecordingPresenter();

        new SearchInteractor(dataAccess, presenter).loadMore(new SearchInputData("hi", 4));

        assertEquals(List.of(4, 5, 6), dataAccess.pagesRequested);
        assertEquals(7, presenter.success.getNextPage());
        assertTrue(presenter.success.isAppending(),
                "loading more adds to the results rather than replacing them");
    }

    @Test
    void theTotalIsWhatExistsNotWhatWasFetched() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchInteractor(new FakePagedDataAccess(500, 20), presenter)
                .execute(new SearchInputData("hi"));

        assertEquals(60, presenter.success.getResults().size(), "three pages were fetched");
        assertEquals(10000, presenter.success.getTotalResults(),
                "but the count shown to the user is the whole result set");
    }

    @Test
    void theKeywordIsCarriedBackSoMoreCanBeRequested() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new SearchInteractor(new FakePagedDataAccess(500, 20), presenter)
                .execute(new SearchInputData("hi"));

        assertEquals("hi", presenter.success.getKeyword());
    }

    @Test
    void blankKeywordFailsWithoutCallingTheSource() {
        final FakePagedDataAccess dataAccess = new FakePagedDataAccess(500, 20);
        final RecordingPresenter presenter = new RecordingPresenter();

        new SearchInteractor(dataAccess, presenter).execute(new SearchInputData("   "));

        assertTrue(presenter.failure != null, "a blank keyword should fail");
        assertTrue(dataAccess.pagesRequested.isEmpty(), "a blank keyword should not hit the source");
    }
}
