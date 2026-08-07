package data_access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.StandardUser;
import entity.User;

/** Tests for searching users in the in-memory store. */
class InMemoryUserSearchTest {

    private InMemoryUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryUserDataAccessObject();
        dataAccess.save(new StandardUser("kiersten", "Kiersten", "pw", "q", "a"));
        dataAccess.save(new StandardUser("lily", "Lily Fan", "pw", "q", "a"));
        dataAccess.save(new StandardUser("enzo", "Tanay", "pw", "q", "a"));
    }

    private List<String> usernamesFrom(List<User> users) {
        final List<String> names = new ArrayList<>();
        for (User user : users) {
            names.add(user.getUsername());
        }
        return names;
    }

    @Test
    void matchesOnPartialUsername() {
        assertEquals(List.of("kiersten"), usernamesFrom(dataAccess.search("kier")));
    }

    @Test
    void matchesOnDisplayNameNotJustUsername() {
        assertEquals(List.of("enzo"), usernamesFrom(dataAccess.search("Tanay")),
                "searching a display name should find the account behind it");
    }

    @Test
    void ignoresCase() {
        assertEquals(List.of("kiersten"), usernamesFrom(dataAccess.search("KIERSTEN")));
        assertEquals(List.of("enzo"), usernamesFrom(dataAccess.search("tanay")));
    }

    @Test
    void matchesInTheMiddleOfAName() {
        assertEquals(List.of("lily"), usernamesFrom(dataAccess.search("ly Fa")));
    }

    @Test
    void returnsEveryMatchWhenSeveralAccountsQualify() {
        final List<String> found = usernamesFrom(dataAccess.search("i"));
        assertTrue(found.contains("kiersten"), "kiersten contains an i");
        assertTrue(found.contains("lily"), "lily contains an i");
        assertEquals(2, found.size());
    }

    @Test
    void returnsNothingWhenNobodyMatches() {
        assertTrue(dataAccess.search("zzzz").isEmpty());
    }

    /** Regex metacharacters must be treated as ordinary text. */
    @Test
    void regexCharactersAreLiteralNotWildcards() {
        assertTrue(dataAccess.search(".*").isEmpty(), "'.*' should match nobody, not everybody");
        assertTrue(dataAccess.search("(").isEmpty(), "an unbalanced bracket should return nothing, not throw");
    }
}
