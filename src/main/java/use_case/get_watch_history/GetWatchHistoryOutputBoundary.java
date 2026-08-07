package use_case.get_watch_history;

public interface GetWatchHistoryOutputBoundary {

    /**
     * Prepares the success view when calling the watch history use case.
     */
    void prepareSuccessView(GetWatchHistoryOutputData response);

    /**
     * Switches to the Personal Account View.
     */
    void switchToPersonalAccountView();

    /**
     * Switches to the Other Account View.
     */
    void switchToOtherAccountView();
}
