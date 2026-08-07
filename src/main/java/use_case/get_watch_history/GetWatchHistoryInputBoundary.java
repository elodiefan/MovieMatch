package use_case.get_watch_history;

public interface GetWatchHistoryInputBoundary {
    /**
     * Executes the get watch history use case.
     */
    void execute(GetWatchHistoryInputData getListsInputData);

    /**
     * Executes the switch to account view use case.
     */
    void switchToAccountView(GetWatchHistoryInputData getListsInputData);
}
