package database;

import java.util.Set;

import entity.StandardUser;
import entity.User;
import entity.UserLists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests the offline user data store without requiring MongoDB. */
class InMemoryUserDataAccessObjectTest {

    private InMemoryUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryUserDataAccessObject();
    }

    @Test
    void savesReadsAndDeletesUsers() {
        final User alice = new StandardUser(
                "alice", "Alice", "old-password", "Pet?", "Milo");

        assertFalse(dataAccess.existsByName("alice"));
        dataAccess.save(alice);
        assertTrue(dataAccess.existsByName("alice"));
        assertTrue(dataAccess.existsByUsername("alice"));
        assertSame(alice, dataAccess.get("alice"));

        dataAccess.saveUser("bob", "Bob", "password", "City?", "Toronto");
        assertEquals("Bob", dataAccess.getDisplayName("bob"));

        dataAccess.deleteAccount(alice);
        assertFalse(dataAccess.existsByUsername("alice"));
        assertNull(dataAccess.get("alice"));
    }

    @Test
    void currentUserProvidesSecurityInformation() {
        dataAccess.saveUser("alice", "Alice", "password", "Pet?", "Milo");
        dataAccess.setCurrentUsername("alice");

        assertEquals("alice", dataAccess.getCurrentUsername());
        assertEquals("Pet?", dataAccess.getSecurityQuestion());
        assertEquals("Milo", dataAccess.getCurrentSecurityAnswer());
    }

    @Test
    void changesPasswordDisplayNameAndUsername() {
        dataAccess.saveUser("alice", "Alice", "old", "Pet?", "Milo");
        dataAccess.addToWatchlist(
                "alice", 10, "movie", "Arrival", "/arrival.jpg", "today");

        dataAccess.changePassword("alice", "new");
        assertEquals("new", dataAccess.get("alice").getPassword());
        assertEquals("", dataAccess.get("alice").getWatchlist());

        dataAccess.changeDisplayName("alice", "Alice Cooper");
        assertEquals("Alice Cooper", dataAccess.getDisplayName("alice"));
        assertTrue(dataAccess.getLists("alice").getWatchlistItems().isEmpty());

        dataAccess.changeUsername("alice", "alice2");
        assertNull(dataAccess.get("alice"));
        assertEquals("alice2", dataAccess.get("alice2").getUsername());
        assertEquals("Alice Cooper", dataAccess.get("alice2").getDisplayName());
        assertTrue(dataAccess.getLists("alice2").getWatchlistItems().isEmpty());
    }

    @Test
    void changesForMissingUserAreNoOps() {
        dataAccess.changePassword("missing", "new");
        dataAccess.changeDisplayName("missing", "New Name");
        dataAccess.changeUsername("missing", "new-name");
        dataAccess.addToWatchlist(
                "missing", 1, "movie", "Missing", null, "today");
        dataAccess.addToWatchHistory(
                "missing", 1, "movie", "Missing", null, "today");

        assertNull(dataAccess.get("missing"));
        assertFalse(dataAccess.existsByUsername("new-name"));
    }

    @Test
    void watchlistAndHistoryTrackMediaAndMoveWatchedItem() {
        dataAccess.saveUser("alice", "Alice", "password", "Pet?", "Milo");
        dataAccess.addToWatchlist(
                "alice", 10, "movie", "Arrival", "/arrival.jpg", "Monday");
        dataAccess.addToWatchlist(
                "alice", 20, "tv", "Severance", "/severance.jpg", "Tuesday");

        assertEquals(Set.of(10, 20), dataAccess.findEngagedMediaIds("alice"));
        assertFalse(dataAccess.hasWatchedMedia("alice", 10, "movie"));

        dataAccess.addToWatchHistory(
                "alice", 10, "movie", "Arrival", "/arrival.jpg", "Friday");

        final UserLists lists = dataAccess.getLists("alice");
        assertEquals("Severance -- Tuesday\n", lists.getWatchlist());
        assertEquals(1, lists.getWatchlistItems().size());
        assertEquals(20, lists.getWatchlistItems().get(0).getMediaId());
        assertEquals("Arrival -- Friday\n", lists.getWatchHistory());
        assertEquals(1, lists.getWatchHistoryItems().size());
        assertTrue(dataAccess.hasWatchedMedia("alice", 10, "movie"));
        assertFalse(dataAccess.hasWatchedMedia("alice", 10, "tv"));
        assertTrue(dataAccess.findEngagedMediaIds("missing").isEmpty());

        final Set<Integer> returnedIds = dataAccess.findEngagedMediaIds("alice");
        returnedIds.clear();
        assertEquals(Set.of(10, 20), dataAccess.findEngagedMediaIds("alice"));
    }

    @Test
    void placeholderBlockingAndMessagingMethodsRemainSafe() {
        assertFalse(dataAccess.alreadyBlocked("bob"));
        assertFalse(dataAccess.inBlockList("bob"));
        assertFalse(dataAccess.canMessage("bob"));
        dataAccess.addToBlockList("bob");
        dataAccess.removeFromBlockList("bob");
        dataAccess.close();
    }
}
