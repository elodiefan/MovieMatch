package interface_adapter.search_result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

class SearchResultViewModelTest {

    @Test
    void constructorCreatesStateAndUsesResultViewName() {
        final SearchResultViewModel viewModel = new SearchResultViewModel();

        assertEquals("search result", SearchResultViewModel.VIEW_NAME);
        assertEquals("Search Results", SearchResultViewModel.TITLE_LABEL);
        assertEquals(SearchResultViewModel.VIEW_NAME, viewModel.getViewName());
        assertNotNull(viewModel.getState());
    }

    @Test
    void firingChangePublishesCurrentState() {
        final SearchResultViewModel viewModel = new SearchResultViewModel();
        final SearchResultState replacement = new SearchResultState();
        replacement.setKeyword("dark");
        viewModel.setState(replacement);
        final AtomicReference<PropertyChangeEvent> event =
                new AtomicReference<>();
        viewModel.addPropertyChangeListener(event::set);

        viewModel.firePropertyChanged();

        assertEquals("state", event.get().getPropertyName());
        assertSame(replacement, event.get().getNewValue());
    }
}
