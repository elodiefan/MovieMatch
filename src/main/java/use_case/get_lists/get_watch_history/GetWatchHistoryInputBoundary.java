package use_case.get_lists.get_watch_history;

public interface GetWatchHistoryInputBoundary {
    /**
     * Executes the get watch history use case.
     * @param getListsInputData the input data
     */
    void execute(GetWatchHistoryInputData getListsInputData);

    /**
     * Executes the switch to account view use case.
     * @param getListsInputData the input data
     */
    void switchToAccountView(GetWatchHistoryInputData getListsInputData);
}
