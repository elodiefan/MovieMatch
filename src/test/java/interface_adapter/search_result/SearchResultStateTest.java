package interface_adapter.search_result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class SearchResultStateTest {

    @Test
    void defaultStateRepresentsAnEmptyFirstPage() {
        final SearchResultState state = new SearchResultState();

        assertTrue(state.getOriginalResults().isEmpty());
        assertTrue(state.getResults().isEmpty());
        assertEquals("", state.getKeyword());
        assertEquals(1, state.getNextPage());
        assertFalse(state.isMoreAvailable());
        assertEquals(0, state.getTotalResults());
        assertNull(state.getFilterError());
    }

    @Test
    void settersUpdateAllSearchAndFilterState() {
        final SearchResultState state = new SearchResultState();
        final List<SearchResultRow> original = List.of(row(1));
        final List<SearchResultRow> filtered = List.of(row(2));

        state.setOriginalResults(original);
        state.setResults(filtered);
        state.setKeyword("arrival");
        state.setNextPage(4);
        state.setMoreAvailable(true);
        state.setTotalResults(42);
        state.setFilterError("Invalid year range");

        assertSame(original, state.getOriginalResults());
        assertSame(filtered, state.getResults());
        assertEquals("arrival", state.getKeyword());
        assertEquals(4, state.getNextPage());
        assertTrue(state.isMoreAvailable());
        assertEquals(42, state.getTotalResults());
        assertEquals("Invalid year range", state.getFilterError());
    }

    private static SearchResultRow row(int id) {
        return new SearchResultRow(id, "movie", "Title", 2020, 8.0,
                List.of("Drama"), List.of(18), "en", "", "");
    }
}
