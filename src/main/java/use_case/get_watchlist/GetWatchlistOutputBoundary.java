package use_case.get_watchlist;

/**
 * The output boundary for the List View Use Case.
 */
public interface GetWatchlistOutputBoundary {

    /**
     * Prepares the success view when calling the watchlist use case.
     * @param response the output boundary for the success view.
     */
    void prepareSuccessView(GetWatchlistOutputData response);

    /**
     * Switches to the Personal Account View.
     */
    void switchToPersonalAccountView();

    /**
     * Switches to the Other Account View.
     */
    void switchToOtherAccountView();
}
