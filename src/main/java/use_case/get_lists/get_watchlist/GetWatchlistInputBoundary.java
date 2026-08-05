package use_case.get_lists.get_watchlist;

import use_case.get_lists.GetListsInputData;

/**
 * Input Boundary for actions which are related to user's watchlist.
 */
public interface GetWatchlistInputBoundary {

    /**
     * Executes the get watchlist use case.
     * @param getListsInputData the input data for the get watchlist use case.
     */
    void execute(GetListsInputData getListsInputData);

    /**
     * Executes the switch to account view use case.
     */
    void switchToAccountView();
}
