package interface_adapter.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

class SearchControllerTest {

    @Test
    void executeCreatesFreshSearchInput() {
        final RecordingSearchInteractor interactor =
                new RecordingSearchInteractor();
        final SearchController controller = new SearchController(interactor);

        controller.execute("arrival");

        assertEquals("arrival", interactor.executed.getKeyword());
        assertEquals(1, interactor.executed.getStartPage());
        assertNull(interactor.loadedMore);
    }

    @Test
    void loadMoreCarriesKeywordAndRequestedStartPage() {
        final RecordingSearchInteractor interactor =
                new RecordingSearchInteractor();
        final SearchController controller = new SearchController(interactor);

        controller.loadMore("arrival", 4);

        assertEquals("arrival", interactor.loadedMore.getKeyword());
        assertEquals(4, interactor.loadedMore.getStartPage());
        assertNull(interactor.executed);
    }

    private static class RecordingSearchInteractor
            implements SearchInputBoundary {
        private SearchInputData executed;
        private SearchInputData loadedMore;

        @Override
        public void execute(SearchInputData searchInputData) {
            executed = searchInputData;
        }

        @Override
        public void loadMore(SearchInputData searchInputData) {
            loadedMore = searchInputData;
        }
    }
}
