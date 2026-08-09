package use_case.get_lists.get_watch_history;

/**
 * Input data for the get watch history use case.
 */
public class GetWatchHistoryInputData {

    private final String username;
    private final String displayName;

    public GetWatchHistoryInputData(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }
}
