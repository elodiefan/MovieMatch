package use_case.get_lists.get_watch_history;

public interface GetWatchHistoryOutputBoundary {

    /**
     * Prepares the success view when calling the watch history use case.
     * @param response the output boundary for the success view.
     */
    void prepareSuccessView(GetWatchHistoryOutputData response);

    /**
     * Switches to the Account View.
     */
    void switchToAccountView();
}
