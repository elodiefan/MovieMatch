package data_access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.client.model.Filters;
import entity.StandardUser;
import entity.User;
import entity.UserLists;
import org.bson.Document;

/**
 * In-memory implementation of {@link UserDataAccessObject}.
 * <p>
 * Stores users in a plain map, so the app and its tests can run with no network
 * and no database. Because it implements the same interface as
 * {@link MongoUserDataAccessObject}, switching between them is a one-line change
 * in {@code AppBuilder}. All data is lost when the program exits.
 */
public class InMemoryUserDataAccessObject implements UserDataAccessObject {

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;

    // ---------- Signup + Login ----------

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    /**
     * Same check as {@link #existsByName}, under the name the login use case
     * uses. Signup calls it existsByName and login calls it existsByUsername,
     * so both are provided; there is only one implementation.
     * @param username the account to look for
     * @return true if the account exists
     */
    @Override
    public boolean existsByUsername(String username) {
        return existsByName(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    // ---------- Logout ----------

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    // ---------- Reset password (after the security question is answered) ----------

    @Override
    public void changePassword(String username, String newPassword) {
        final User old = users.get(username);
        if (old == null) {
            return;
        }
        // StandardUser is immutable, so rebuild it with the new password.
        users.put(username, new StandardUser(old.getUsername(), old.getDisplayName(),
                newPassword, old.getSecurityQuestion(), old.getAnswer()));
    }

    // ---------- Get watchlist ----------

    @Override
    public UserLists getLists(String username) {
        final User user = users.get(username);
        return user.getUserLists();
    }

    // ---------- Delete account (after the security question is answered) ----------

    @Override
    public void deleteAccount(User user) {
        users.remove(user.getUsername());
    }

    @Override
    public String getCurrentSecurityAnswer() {
        final User currentUser = users.get(currentUsername);
        return currentUser.getAnswer();
    }

    // ---------- Home page ----------
    @Override
    public String getDisplayName() {
        return users.get(currentUsername).getDisplayName();
    }

    // ---------- Account page ----------
    @Override
    public String getSecurityQuestion() {
        return users.get(currentUsername).getSecurityQuestion();
    }

    // ---------- Nothing to release ----------

    @Override
    public void close() {
        // No resources to free for an in-memory store.
    }
}
