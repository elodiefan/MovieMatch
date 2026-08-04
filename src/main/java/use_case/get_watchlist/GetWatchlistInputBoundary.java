package use_case.get_watchlist;

/**
 * Input Boundary for actions which are related to user's watchlist.
 */
public interface GetWatchlistInputBoundary {

    /**
     * Executes the get watchlist use case.
     * @param getWatchlistInputData the input data for the get watchlist use case.
     */
    void execute(GetWatchlistInputData getWatchlistInputData);

    /**
     * Executes the switch to account view use case.
     */
    void switchToAccountView();
}
