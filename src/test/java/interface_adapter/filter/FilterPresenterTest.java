package interface_adapter.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interface_adapter.search_result.SearchResultRow;
import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.filter.FilterOutputData;
import use_case.search.MediaResultData;

class FilterPresenterTest {

    private SearchResultViewModel viewModel;
    private FilterPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new SearchResultViewModel();
        presenter = new FilterPresenter(viewModel);
    }

    @Test
    void successConvertsResultsClearsErrorAndNotifiesView() {
        viewModel.getState().setFilterError("old error");
        final AtomicReference<PropertyChangeEvent> event =
                new AtomicReference<>();
        viewModel.addPropertyChangeListener(event::set);
        final MediaResultData media = new MediaResultData(101, "movie",
                "Arrival", 2016, 7.9, List.of("Drama", "Sci-Fi"),
                List.of(18, 878), "en", "First contact", "/arrival.jpg");

        presenter.prepareSuccessView(new FilterOutputData(List.of(media)));

        final SearchResultState state = viewModel.getState();
        assertNull(state.getFilterError());
        assertEquals(1, state.getResults().size());
        final SearchResultRow row = state.getResults().get(0);
        assertEquals(101, row.getMediaId());
        assertEquals("movie", row.getMediaType());
        assertEquals("Arrival", row.getTitle());
        assertEquals(2016, row.getReleaseYear());
        assertEquals(7.9, row.getAverageRating());
        assertEquals(List.of("Drama", "Sci-Fi"), row.getGenreNames());
        assertEquals(List.of(18, 878), row.getGenreIds());
        assertEquals("en", row.getLanguage());
        assertEquals("First contact", row.getOverview());
        assertEquals("/arrival.jpg", row.getPosterPath());
        assertSame(state, event.get().getNewValue());
    }

    @Test
    void successCanPublishAnEmptyFilteredList() {
        presenter.prepareSuccessView(new FilterOutputData(List.of()));

        assertEquals(List.of(), viewModel.getState().getResults());
        assertNull(viewModel.getState().getFilterError());
    }

    @Test
    void failurePreservesResultsSetsErrorAndNotifiesOnce() {
        final List<SearchResultRow> existing = List.of(new SearchResultRow(
                1, "movie", "Arrival", 2016, 7.9, List.of("Drama"),
                List.of(18), "en", "", ""));
        viewModel.getState().setResults(existing);
        final AtomicInteger events = new AtomicInteger();
        viewModel.addPropertyChangeListener(event -> events.incrementAndGet());

        presenter.prepareFailView("Invalid rating");

        assertSame(existing, viewModel.getState().getResults());
        assertEquals("Invalid rating", viewModel.getState().getFilterError());
        assertEquals(1, events.get());
    }
}
