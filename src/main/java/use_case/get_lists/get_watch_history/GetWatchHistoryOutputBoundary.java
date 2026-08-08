package use_case.get_lists.get_watch_history;

import use_case.get_lists.GetListsOutputBoundary;

public interface GetWatchHistoryOutputBoundary extends GetListsOutputBoundary {

    /**
     * Prepares the success view when calling the watch history use case.
     * @param response the output boundary for the success view.
     */
    void prepareSuccessView(GetWatchHistoryOutputData response);

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
