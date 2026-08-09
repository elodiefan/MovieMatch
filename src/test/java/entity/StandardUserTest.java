package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StandardUserTest {

    @Test
    void constructorStoresAccountInformationAndCreatesEmptyLists() {
        final StandardUser user = new StandardUser(
                "bob", "Bob", "password1", "First pet?", "Mochi");

        assertEquals("bob", user.getUsername());
        assertEquals("Bob", user.getDisplayName());
        assertEquals("password1", user.getPassword());
        assertEquals("First pet?", user.getSecurityQuestion());
        assertEquals("Mochi", user.getAnswer());
        assertEquals("bob", user.getUserLists().getUsername());
        assertTrue(user.getWatchlist().isEmpty());
        assertTrue(user.getWatchHistory().isEmpty());
        assertTrue(user.getBlockedUsers().isEmpty());
    }

    @Test
    void suppliedListsAreReturnedAndCanBeReplaced() {
        final UserLists original = new UserLists("bob", "one", "two", "three");
        final StandardUser user = new StandardUser(
                "bob", "Bob", "password1", "First pet?", "Mochi", original);

        assertSame(original, user.getUserLists());
        assertEquals("one", user.getWatchlist());
        assertEquals("two", user.getWatchHistory());
        assertEquals("three", user.getBlockedUsers());

        final UserLists replacement = new UserLists("bob", "new", "", "");
        user.setUserLists(replacement);
        assertSame(replacement, user.getUserLists());
        assertEquals("new", user.getWatchlist());
    }
}
