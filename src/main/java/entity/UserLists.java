package entity;

import java.util.List;

/**
 * Class representing the lists of a given user.
 */

public class UserLists {

    private final String username;
    private final List<Integer> watchlist;
    private final List<Integer> watchHistory;
    private final List<Integer> reviews;
    private final List<String> blockedUsers;

    public UserLists(String username, List<Integer> watchlist,
                     List<Integer> watchHistory, List<Integer> reviews,
                     List<String> blockedUsers) {
        this.username = username;
        this.watchlist = watchlist;
        this.watchHistory = watchHistory;
        this.reviews = reviews;
        this.blockedUsers = blockedUsers;
    }

    public String getUsername() {
        return username;
    }

    public List<Integer> getWatchlist() {
        return watchlist;
    }

    public List<Integer> getWatchHistory() {
        return watchHistory;
    }

    public List<Integer> getReviews() {
        return reviews;
    }

    public List<String> getBlockedUsers() {
        return blockedUsers;
    }
}
