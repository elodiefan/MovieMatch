package use_case.get_watchlist;

/**
 * Input Data for the Watchlist View Use Case.
 */
public class GetWatchlistInputData {

    private final String username;
    private final String dislayName;

    public GetWatchlistInputData(String username, String displayName) {
        this.username = username;
        this.dislayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getDislayName() {
        return dislayName;
    }
}
