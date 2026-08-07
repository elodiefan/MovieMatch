package use_case.get_watchlist;

/**
 * Input Boundary for actions which are related to user's watchlist.
 */
public interface GetWatchlistInputBoundary {

    /**
     * Executes the get watchlist use case.
     */
    void execute(GetWatchlistInputData getListsInputData);

    /**
     * Executes the switch to account view use case.
     */
    void switchToAccountView(GetWatchlistInputData getListsInputData);
}
