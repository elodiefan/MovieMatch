package use_case.get_lists.get_watch_history;

import java.util.ArrayList;
import java.util.List;

/**
 * Output Data for the Watch History View Use Case.
 */
public class GetWatchHistoryOutputData {

    private final String username;
    private final String displayName;
    private final String watchHistory;

    private final List<WatchHistoryItemData> watchHistoryItems;

    public GetWatchHistoryOutputData(String username, String displayName,
                                     String watchHistory, List<WatchHistoryItemData> watchHistoryItems) {
        this.username = username;
        this.displayName = displayName;
        this.watchHistory = watchHistory;
        this.watchHistoryItems = watchHistoryItems;
    }

    public GetWatchHistoryOutputData(String username, String displayName, String watchHistory) {
        this.username = username;
        this.displayName = displayName;
        this.watchHistory = watchHistory;
        this.watchHistoryItems = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getWatchHistory() {
        return watchHistory;
    }

    public List<WatchHistoryItemData> getWatchHistoryItems() {
        return watchHistoryItems;
    }
}

