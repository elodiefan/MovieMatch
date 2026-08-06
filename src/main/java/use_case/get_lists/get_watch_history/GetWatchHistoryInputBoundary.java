package use_case.get_lists.get_watch_history;

import use_case.get_lists.GetListsInputData;

public interface GetWatchHistoryInputBoundary {
    /**
     * Executes the get watch history use case.
     * @param getListsInputData the input data for the get watch history use case.
     */
    void execute(GetListsInputData getListsInputData);

    /**
     * Executes the switch to account view use case.
     * @param getListsInputData the input data for the use case.
     */
    void switchToAccountView(GetListsInputData getListsInputData);
}

