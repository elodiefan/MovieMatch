package use_case.get_lists.get_watch_history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GetWatchHistoryDataTest {

    @Test
    void inputDataReturnsAccountInformation() {
        final GetWatchHistoryInputData data = new GetWatchHistoryInputData("bob", "Bob");

        assertEquals("bob", data.getUsername());
        assertEquals("Bob", data.getDisplayName());
    }

    @Test
    void basicOutputDataReturnsTextAndEmptyItemList() {
        final GetWatchHistoryOutputData data = new GetWatchHistoryOutputData(
                "bob", "Bob", "Example Movie");

        assertEquals("bob", data.getUsername());
        assertEquals("Bob", data.getDisplayName());
        assertEquals("Example Movie", data.getWatchHistory());
        assertTrue(data.getWatchHistoryItems().isEmpty());
    }
}
