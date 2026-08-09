package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PremiumUserTest {

    @Test
    void premiumUserKeepsStandardUserInformation() {
        final PremiumUser user = new PremiumUser(
                "bob", "Bob", "password1", "First pet?", "Mochi");

        assertTrue(user instanceof Customizable);
        assertEquals("bob", user.getUsername());
        assertEquals("Bob", user.getDisplayName());
        assertEquals("password1", user.getPassword());
        assertEquals("First pet?", user.getSecurityQuestion());
        assertEquals("Mochi", user.getAnswer());
        assertEquals("bob", user.getUserLists().getUsername());
    }

    @Test
    void premiumUserCanUseSuppliedLists() {
        final UserLists lists = new UserLists("bob", "watchlist", "history", "blocked");
        final PremiumUser user = new PremiumUser(
                "bob", "Bob", "password1", "First pet?", "Mochi", lists);

        assertSame(lists, user.getUserLists());
    }
}
