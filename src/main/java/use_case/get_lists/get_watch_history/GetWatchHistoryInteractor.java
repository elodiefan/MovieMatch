package use_case.get_lists.get_watch_history;

import use_case.get_lists.GetListsInputData;
import use_case.get_lists.GetListsUserDataAccessInterface;

/**
 * The Watch History View Interactor.
 */

public class GetWatchHistoryInteractor implements GetWatchHistoryInputBoundary {

    private final GetListsUserDataAccessInterface userDataAccessObject;
    private final GetWatchHistoryOutputBoundary getListsPresenter;

    public GetWatchHistoryInteractor(GetListsUserDataAccessInterface userDataAccessInterface,
                                  GetWatchHistoryOutputBoundary getWatchHistoryOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.getListsPresenter = getWatchHistoryOutputBoundary;
    }

    @Override
    public void execute(GetListsInputData getListsInputData) {
        final String username = getListsInputData.getUsername();
        final String displayName = getListsInputData.getDislayName();
        final String watchHistory = userDataAccessObject.getLists(username).getWatchHistory();
        final GetWatchHistoryOutputData getWatchHistoryOutputData = new GetWatchHistoryOutputData(username,
                displayName, watchHistory);
        getListsPresenter.prepareSuccessView(getWatchHistoryOutputData);
    }

    /**
     * Switches from list view to account view.
     */
    @Override
    public void switchToAccountView(GetListsInputData getListsInputData) {
        if (userDataAccessObject.getCurrentUsername().equals(getListsInputData.getUsername())) {
            getListsPresenter.switchToPersonalAccountView();
        }
        else {
            getListsPresenter.switchToOtherAccountView();
        }
    }
}
