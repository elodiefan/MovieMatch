package use_case.get_lists.get_watchlist;

import use_case.get_lists.GetListsOutputBoundary;

/**
 * The output boundary for the List View Use Case.
 */
public interface GetWatchListOutputBoundary extends GetListsOutputBoundary {

    /**
     * Prepares the success view when calling the watchlist use case.
     * @param response the output boundary for the success view.
     */
    void prepareSuccessView(GetWatchlistOutputData response);

    /**
     * Switches to the Personal Account View.
     */
    @Override
    void switchToPersonalAccountView();

    /**
     * Switches to the Other Account View.
     */
    @Override
    void switchToOtherAccountView();
}
