package use_case.get_lists.get_watchlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GetWatchlistDataTest {

    @Test
    void inputDataReturnsAccountInformation() {
        final GetWatchlistInputData data = new GetWatchlistInputData("bob", "Bob");

        assertEquals("bob", data.getUsername());
        assertEquals("Bob", data.getDisplayName());
    }

    @Test
    void basicOutputDataReturnsTextAndEmptyItemList() {
        final GetWatchlistOutputData data = new GetWatchlistOutputData(
                "bob", "Bob", "Example Movie");

        assertEquals("bob", data.getUsername());
        assertEquals("Bob", data.getDisplayName());
        assertEquals("Example Movie", data.getWatchlist());
        assertTrue(data.getWatchlistItems().isEmpty());
    }
}
