package data_access;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entity.StandardUser;
import entity.User;
import entity.UserLists;
import org.junit.jupiter.api.Test;

/**
 * Tests for in-memory watchlist and watch history storage.
 */
class InMemoryUserListsDataAccessObjectTest {

    @Test
    void addToWatchlistAppendsMediaLog() {
        final InMemoryUserDataAccessObject dao =
                new InMemoryUserDataAccessObject();
        final User user = makeUser();
        dao.save(user);

        dao.addToWatchlist("elodie", 550, "movie", "Fight Club",
                "2026-08-07T10:00:00-04:00");

        assertEquals("Fight Club -- 2026-08-07T10:00:00-04:00\n",
                dao.getLists("elodie").getWatchlist());
        assertEquals("", dao.getLists("elodie").getWatchHistory());
    }

    @Test
    void addToWatchHistoryAppendsMediaLog() {
        final InMemoryUserDataAccessObject dao =
                new InMemoryUserDataAccessObject();
        final User user = makeUser();
        dao.save(user);

        dao.addToWatchHistory("elodie", 550, "movie", "Fight Club",
                "2026-08-07T10:00:00-04:00");

        assertEquals("", dao.getLists("elodie").getWatchlist());
        assertEquals("Fight Club -- 2026-08-07T10:00:00-04:00\n",
                dao.getLists("elodie").getWatchHistory());
    }

    @Test
    void addToWatchlistKeepsExistingWatchHistory() {
        final InMemoryUserDataAccessObject dao =
                new InMemoryUserDataAccessObject();
        final User user = new StandardUser("elodie", "Elodie", "password",
                "Question?", "Answer",
                new UserLists("elodie", "", "Dune -- yesterday\n", ""));
        dao.save(user);

        dao.addToWatchlist("elodie", 550, "movie", "Fight Club",
                "today");

        assertEquals("Fight Club -- today\n",
                dao.getLists("elodie").getWatchlist());
        assertEquals("Dune -- yesterday\n",
                dao.getLists("elodie").getWatchHistory());
    }

    private User makeUser() {
        return new StandardUser("elodie", "Elodie", "password",
                "Question?", "Answer");
    }
}
