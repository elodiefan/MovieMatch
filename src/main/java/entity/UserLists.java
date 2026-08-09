package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the lists of a given user.
 */

public class UserLists {

    private final String username;
    private final String watchlist;
    private final String watchHistory;
    private final String blockedUsers;
    private final List<MediaListItem> watchlistItems;
    private final List<MediaListItem> watchHistoryItems;

    public UserLists(String username, String watchlist,
                     String watchHistory, String blockedUsers) {
        this(username, watchlist, watchHistory, blockedUsers,
                new ArrayList<>(), new ArrayList<>());
    }

    public UserLists(String username, String watchlist,
                     String watchHistory, String blockedUsers,
                     List<MediaListItem> watchlistItems,
                     List<MediaListItem> watchHistoryItems) {
        this.username = username;
        this.watchlist = watchlist;
        this.watchHistory = watchHistory;
        this.blockedUsers = blockedUsers;
        this.watchlistItems = new ArrayList<>(watchlistItems);
        this.watchHistoryItems = new ArrayList<>(watchHistoryItems);
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

    public List<MediaListItem> getWatchlistItems() {
        return new ArrayList<>(watchlistItems);
    }

    public List<MediaListItem> getWatchHistoryItems() {
        return new ArrayList<>(watchHistoryItems);
    }
}
