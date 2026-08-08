package use_case.get_watchlist;

/**
 * Input data for the get watchlist use case.
 */
public class GetWatchlistInputData {

    private final String username;
    private final String displayName;

    public GetWatchlistInputData(String username, String displayName) {
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
