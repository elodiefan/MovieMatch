package use_case.get_lists.get_watch_history;

/**
 * Output Data for the Watch History View Use Case.
 */
public class GetWatchHistoryOutputData {

    private final String username;
    private final String displayName;
    private final String watchHistory;

    public GetWatchHistoryOutputData(String username, String displayName, String watchHistory) {
        this.username = username;
        this.displayName = displayName;
        this.watchHistory = watchHistory;
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
}

