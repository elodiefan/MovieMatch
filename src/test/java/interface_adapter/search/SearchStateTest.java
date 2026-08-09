package interface_adapter.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SearchStateTest {

    @Test
    void errorIsInitiallyNullAndCanBeChanged() {
        final SearchState state = new SearchState();

        assertNull(state.getSearchError());

        state.setSearchError("Search failed");

        assertEquals("Search failed", state.getSearchError());
    }
}
