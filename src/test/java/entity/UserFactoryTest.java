package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UserFactoryTest {

    @Test
    void standardFactoryCreatesStandardUsersWithDefaultLists() {
        final User user = new StandardUserFactory().create(
                "bob", "Bob", "password1", "First pet?", "Mochi");

        assertTrue(user instanceof StandardUser);
        assertEquals("bob", user.getUsername());
        assertEquals("Bob", user.getDisplayName());
        assertTrue(user.getWatchlist().isEmpty());
    }

    @Test
    void standardFactoryUsesSuppliedLists() {
        final UserLists lists = new UserLists("bob", "watchlist", "history", "blocked");
        final User user = new StandardUserFactory().create(
                "bob", "Bob", "password1", "First pet?", "Mochi", lists);

        assertSame(lists, user.getUserLists());
    }

    @Test
    void premiumFactoryCreatesCustomizableUsersWithDefaultLists() {
        final User user = new PremiumUserFactory().create(
                "bob", "Bob", "password1", "First pet?", "Mochi");

        assertTrue(user instanceof PremiumUser);
        assertTrue(user instanceof Customizable);
        assertEquals("bob", user.getUsername());
        assertTrue(user.getWatchlist().isEmpty());
    }

    @Test
    void premiumFactoryUsesSuppliedLists() {
        final UserLists lists = new UserLists("bob", "watchlist", "history", "blocked");
        final User user = new PremiumUserFactory().create(
                "bob", "Bob", "password1", "First pet?", "Mochi", lists);

        assertSame(lists, user.getUserLists());
    }
}
