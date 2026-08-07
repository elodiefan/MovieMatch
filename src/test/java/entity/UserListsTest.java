package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the UserLists entity.
 */
class UserListsTest {

    @Test
    void constructorStoresListFields() {
        final UserLists userLists = new UserLists("elodie",
                "Fight Club -- 2026-08-07T10:00:00-04:00\n",
                "Dune -- 2026-08-07T11:00:00-04:00\n",
                "blocked-user\n");

        assertEquals("elodie", userLists.getUsername());
        assertEquals("Fight Club -- 2026-08-07T10:00:00-04:00\n",
                userLists.getWatchlist());
        assertEquals("Dune -- 2026-08-07T11:00:00-04:00\n",
                userLists.getWatchHistory());
        assertEquals("blocked-user\n", userLists.getBlockedUsers());
    }
}
