package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.Set;

import entity.StandardUser;
import entity.User;
import entity.UserLists;

/**
 * In-memory implementation of UserDataAccessObject.
 *
 * Stores users in a plain map, so the app and its tests can run with no network
 * and no database. Because it implements the same interface as
 * MongoUserDataAccessObject, switching between them is a one-line change
 * in AppBuilder. All data is lost when the program exits.
 */
public class InMemoryUserDataAccessObject implements UserDataAccessObject {

    private final Map<String, User> users = new HashMap<>();

    /** Watchlist and watch history ids, kept per user for the offline store. */
    private final Map<String, Set<Integer>> engagedMediaIds = new HashMap<>();

    @Override
    public Set<Integer> findEngagedMediaIds(String username) {
        return new LinkedHashSet<>(engagedMediaIds.getOrDefault(username, new LinkedHashSet<>()));
    }

    private String currentUsername;

    // ---------- Signup + Login ----------

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    /**
     * Same check as #existsByName, under the name the login use case
     * uses. Signup calls it existsByName and login calls it existsByUsername,
     * so both are provided; there is only one implementation.
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

    @Override
    public String getDisplayName() {
        return "";
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

    @Override
    public void addToWatchlist(String username, int mediaId,
                               String mediaType, String mediaTitle,
                               String addedAt) {
        final User user = users.get(username);
        if (user != null) {
            final String watchlist = appendMediaLog(user.getWatchlist(),
                    mediaTitle, addedAt);
            user.setUserLists(new UserLists(username, watchlist,
                    user.getWatchHistory(), user.getBlockedUsers()));
            recordEngaged(username, mediaId);
        }
    }

    @Override
    public void addToWatchHistory(String username, int mediaId,
                                  String mediaType, String mediaTitle,
                                  String watchedAt) {
        final User user = users.get(username);
        if (user != null) {
            final String watchHistory = appendMediaLog(user.getWatchHistory(),
                    mediaTitle, watchedAt);
            user.setUserLists(new UserLists(username, user.getWatchlist(),
                    watchHistory, user.getBlockedUsers()));
            recordEngaged(username, mediaId);
        }
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

    // ---------- Get user profile ----------
    @Override
    public String getDisplayName(String username) {
        return users.get(username).getDisplayName();
    }

    // ---------- Get security question ----------
    @Override
        public String getSecurityQuestion() {
        return users.get(currentUsername).getSecurityQuestion();
    }

    // ---------- Search for users ----------

    /**
     * Finds accounts whose username or display name contains the keyword,
     * ignoring case. Same contract as the Mongo version, over a plain map.
     */
    @Override
    public List<User> search(String keyword) {
        final String needle = keyword.toLowerCase();
        final List<User> found = new ArrayList<>();
        for (User user : users.values()) {
            if (matches(user, needle)) {
                found.add(user);
            }
        }
        return found;
    }

    private boolean matches(User user, String lowercaseKeyword) {
        return user.getUsername().toLowerCase().contains(lowercaseKeyword)
                || user.getDisplayName().toLowerCase().contains(lowercaseKeyword);
    }

    // ---------- Block user ----------
    @Override
    public boolean alreadyBlocked(String otherUsername) {
        return false;
    }

    @Override
    public void addToBlockList(String otherUsername) {

    }

    @Override
    public void removeFromBlockList(String otherUsername) {

    }

    // ---------- Access message chat view ----------
    public boolean inBlockList(String otherUsername) {
        return false;
    }

    @Override
    public boolean canMessage(String otherUsername) {
        return false;
    }

    // ---------- Nothing to release ----------

    @Override
    public void close() {
        // No resources to free for an in-memory store.
    }

    private String appendMediaLog(String currentList, String mediaTitle,
                                  String loggedAt) {
        return currentList + mediaTitle + " -- " + loggedAt + "\n";
    }

    /**
     * Remembers the id as well as the display line, since the lists themselves
     * are kept as text and recommendations need something to match on.
     */
    private void recordEngaged(String username, int mediaId) {
        engagedMediaIds.computeIfAbsent(username, key -> new LinkedHashSet<>()).add(mediaId);
    }
}
