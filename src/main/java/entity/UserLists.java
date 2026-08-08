package entity;

/**
 * Class representing the lists of a given user.
 */

public class UserLists {

    private final String username;
    private final String watchlist;
    private final String watchHistory;
    private final String blockedUsers;

    public UserLists(String username, String watchlist,
                     String watchHistory, String blockedUsers) {
        this.username = username;
        this.watchlist = watchlist;
        this.watchHistory = watchHistory;
        this.blockedUsers = blockedUsers;
    }

    public String getUsername() {
        return username;
    }

    public String getWatchlist() {
        return watchlist;
    }

    public String getWatchHistory() {
        return watchHistory;
    }

    public String getBlockedUsers() {
        return blockedUsers;
    }
}
