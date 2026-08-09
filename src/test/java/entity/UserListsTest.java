package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class UserListsTest {

    @Test
    void basicConstructorStoresTextListsAndStartsItemListsEmpty() {
        final UserLists lists = new UserLists(
                "bob", "watchlist", "history", "blocked");

        assertEquals("bob", lists.getUsername());
        assertEquals("watchlist", lists.getWatchlist());
        assertEquals("history", lists.getWatchHistory());
        assertEquals("blocked", lists.getBlockedUsers());
        assertTrue(lists.getWatchlistItems().isEmpty());
        assertTrue(lists.getWatchHistoryItems().isEmpty());
    }

    @Test
    void itemListsAreCopiedWhenStoredAndReturned() {
        final MediaListItem movie = item(1, "Arrival");
        final List<MediaListItem> watchlist = new ArrayList<>(List.of(movie));
        final UserLists lists = new UserLists(
                "bob", "", "", "", watchlist, List.of(item(2, "Dune")));

        watchlist.clear();
        final List<MediaListItem> returned = lists.getWatchlistItems();
        returned.clear();

        assertEquals(1, lists.getWatchlistItems().size());
        assertEquals("Arrival", lists.getWatchlistItems().get(0).getMediaTitle());
        assertEquals("Dune", lists.getWatchHistoryItems().get(0).getMediaTitle());
    }

    private static MediaListItem item(int id, String title) {
        return new MediaListItem(id, "movie", title, "2026-08-08", "");
    }
}
