package use_case.get_watchlist;

/**
 * Output Data for the List View Use Case.
 */
public class GetWatchlistOutputData {

    private final String username;
    private final String displayName;
    private final Object watchlist;

    public GetWatchlistOutputData(String username, String displayName, Object watchlist) {
        this.username = username;
        this.displayName = displayName;
        this.watchlist = watchlist;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Object getWatchlist() {
        return watchlist;
    }
}
