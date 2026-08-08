package use_case.get_watchlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Output Data for the List View Use Case.
 */
public class GetWatchlistOutputData {

    private final String username;
    private final String displayName;
    private final String watchlist;
    private final List<WatchlistItemData> watchlistItems;

    public GetWatchlistOutputData(String username, String displayName, String watchlist,
                                  List<WatchlistItemData> watchlistItems) {
        this.username = username;
        this.displayName = displayName;
        this.watchlist = watchlist;
        this.watchlistItems = watchlistItems;
    }

    public GetWatchlistOutputData(String username, String displayName, String watchlist) {
        this.username = username;
        this.displayName = displayName;
        this.watchlist = watchlist;
        this.watchlistItems = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getWatchlist() {
        return watchlist;
    }

    public List<WatchlistItemData> getWatchlistItems() {
        return watchlistItems;
    }
}
