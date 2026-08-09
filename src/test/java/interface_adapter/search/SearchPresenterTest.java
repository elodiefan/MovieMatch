package interface_adapter.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interface_adapter.ViewManagerModel;
import interface_adapter.search_result.SearchResultRow;
import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.search.MediaResultData;
import use_case.search.SearchOutputData;

class SearchPresenterTest {

    private ViewManagerModel viewManagerModel;
    private SearchViewModel searchViewModel;
    private SearchResultViewModel resultViewModel;
    private SearchPresenter presenter;

    @BeforeEach
    void setUp() {
        viewManagerModel = new ViewManagerModel();
        searchViewModel = new SearchViewModel();
        resultViewModel = new SearchResultViewModel();
        presenter = new SearchPresenter(viewManagerModel, searchViewModel,
                resultViewModel, Runnable::run);
    }

    @Test
    void failureUpdatesSearchStateAndNotifiesListeners() {
        final AtomicReference<PropertyChangeEvent> event =
                new AtomicReference<>();
        searchViewModel.addPropertyChangeListener(event::set);

        presenter.prepareFailView("TMDB unavailable");

        assertEquals("TMDB unavailable",
                searchViewModel.getState().getSearchError());
        assertSame(searchViewModel.getState(), event.get().getNewValue());
    }

    @Test
    void freshSuccessReplacesResultsCopiesMetadataAndChangesView() {
        resultViewModel.getState().setOriginalResults(
                List.of(row(1, "Old result")));
        final List<MediaResultData> freshResults = List.of(
                mediaResult(2, "Arrival"), mediaResult(3, "Dark"));
        final AtomicInteger resultEvents = new AtomicInteger();
        final AtomicInteger viewEvents = new AtomicInteger();
        resultViewModel.addPropertyChangeListener(
                event -> resultEvents.incrementAndGet());
        viewManagerModel.addPropertyChangeListener(
                event -> viewEvents.incrementAndGet());

        presenter.prepareSuccessView(new SearchOutputData(
                freshResults, "arrival", 4, true, false, 42));

        final SearchResultState state = resultViewModel.getState();
        assertEquals(2, state.getOriginalResults().size());
        assertEquals("Arrival", state.getOriginalResults().get(0).getTitle());
        assertEquals("Dark", state.getOriginalResults().get(1).getTitle());
        assertSame(state.getOriginalResults(), state.getResults());
        assertEquals("arrival", state.getKeyword());
        assertEquals(4, state.getNextPage());
        assertTrue(state.isMoreAvailable());
        assertEquals(42, state.getTotalResults());
        assertEquals(SearchResultViewModel.VIEW_NAME,
                viewManagerModel.getState());
        assertEquals(1, resultEvents.get());
        assertEquals(1, viewEvents.get());
    }

    @Test
    void appendedSuccessCombinesResultsWithoutChangingView() {
        final SearchResultRow existing = row(1, "Arrival");
        final MediaResultData added = mediaResult(2, "Dark");
        resultViewModel.getState().setOriginalResults(List.of(existing));
        viewManagerModel.setState(SearchResultViewModel.VIEW_NAME);
        final AtomicInteger viewEvents = new AtomicInteger();
        viewManagerModel.addPropertyChangeListener(
                event -> viewEvents.incrementAndGet());

        presenter.prepareSuccessView(new SearchOutputData(
                List.of(added), "space", 7, false, true, 2));

        final SearchResultState state = resultViewModel.getState();
        assertEquals(2, state.getOriginalResults().size());
        assertSame(existing, state.getOriginalResults().get(0));
        assertEquals("Dark", state.getOriginalResults().get(1).getTitle());
        assertSame(state.getOriginalResults(), state.getResults());
        assertEquals(7, state.getNextPage());
        assertFalse(state.isMoreAvailable());
        assertEquals(0, viewEvents.get());
    }

    @Test
    void updatesAreSubmittedToConfiguredUiExecutor() {
        final QueuedExecutor executor = new QueuedExecutor();
        presenter = new SearchPresenter(viewManagerModel, searchViewModel,
                resultViewModel, executor);

        presenter.prepareFailView("offline");

        assertEquals(1, executor.tasks.size());
        assertEquals(null, searchViewModel.getState().getSearchError());

        executor.tasks.get(0).run();

        assertEquals("offline", searchViewModel.getState().getSearchError());
    }

    private static MediaResultData mediaResult(int id, String title) {
        return new MediaResultData(id, "movie", title, 2020, 8.0,
                List.of("Science Fiction"), List.of(878), "en",
                "Overview for " + title, "/poster.jpg");
    }

    private static SearchResultRow row(int id, String title) {
        return new SearchResultRow(id, "movie", title, 2020, 8.0,
                List.of("Science Fiction"), List.of(878), "en",
                "Overview for " + title, "/poster.jpg");
    }

    private static class QueuedExecutor implements Executor {
        private final List<Runnable> tasks = new ArrayList<>();

        @Override
        public void execute(Runnable command) {
            tasks.add(command);
        }
    }
}
