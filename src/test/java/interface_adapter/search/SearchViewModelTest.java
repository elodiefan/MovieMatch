package interface_adapter.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

class SearchViewModelTest {

    @Test
    void constructorCreatesSearchStateAndUsesSearchViewName() {
        final SearchViewModel viewModel = new SearchViewModel();

        assertEquals("search", SearchViewModel.VIEW_NAME);
        assertEquals("Search", SearchViewModel.TITLE_LABEL);
        assertEquals(SearchViewModel.VIEW_NAME, viewModel.getViewName());
        assertNotNull(viewModel.getState());
    }

    @Test
    void firingChangePublishesCurrentState() {
        final SearchViewModel viewModel = new SearchViewModel();
        final SearchState replacement = new SearchState();
        replacement.setSearchError("offline");
        viewModel.setState(replacement);
        final AtomicReference<PropertyChangeEvent> event =
                new AtomicReference<>();
        viewModel.addPropertyChangeListener(event::set);

        viewModel.firePropertyChanged();

        assertEquals("state", event.get().getPropertyName());
        assertSame(replacement, event.get().getNewValue());
    }
}
