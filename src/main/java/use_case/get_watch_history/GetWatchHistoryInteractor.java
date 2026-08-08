package use_case.get_watch_history;

/**
 * The Watch History View Interactor.
 */

public class GetWatchHistoryInteractor implements GetWatchHistoryInputBoundary {

    private final GetWatchHistoryUserDataAccessInterface userDataAccessObject;
    private final GetWatchHistoryOutputBoundary getListsPresenter;

    public GetWatchHistoryInteractor(GetWatchHistoryUserDataAccessInterface userDataAccessInterface,
                                  GetWatchHistoryOutputBoundary getWatchHistoryOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.getListsPresenter = getWatchHistoryOutputBoundary;
    }

    @Override
    public void execute(GetWatchHistoryInputData getListsInputData) {
        final String username = getListsInputData.getUsername();
        final String displayName = getListsInputData.getDisplayName();
        final String watchHistory = userDataAccessObject.getLists(username).getWatchHistory();
        final GetWatchHistoryOutputData getWatchHistoryOutputData = new GetWatchHistoryOutputData(username,
                displayName, watchHistory);
        getListsPresenter.prepareSuccessView(getWatchHistoryOutputData);
    }

    /**
     * Switches from list view to account view.
     *
     * @param getListsInputData the get lists input data
     */
    @Override
    public void switchToAccountView(GetWatchHistoryInputData getListsInputData) {
        if (userDataAccessObject.getCurrentUsername().equals(getListsInputData.getUsername())) {
            getListsPresenter.switchToPersonalAccountView();
        }
        else {
            getListsPresenter.switchToOtherAccountView();
        }
    }
}
