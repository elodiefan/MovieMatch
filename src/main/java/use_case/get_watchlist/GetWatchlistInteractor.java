package use_case.get_watchlist;

/**
 * The Watchlist View Interactor.
 */

public class GetWatchlistInteractor implements GetWatchlistInputBoundary {

    private final GetWatchlistUserDataAccessInterface userDataAccessObject;
    private final GetWatchlistOutputBoundary getListsPresenter;

    public GetWatchlistInteractor(GetWatchlistUserDataAccessInterface userDataAccessInterface,
                                  GetWatchlistOutputBoundary getWatchListOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.getListsPresenter = getWatchListOutputBoundary;
    }

    @Override
    public void execute(GetWatchlistInputData getListsInputData) {
        final String username = getListsInputData.getUsername();
        final String displayName = getListsInputData.getDisplayName();
        final String watchlist = userDataAccessObject.getLists(username).getWatchlist();
        final GetWatchlistOutputData getWatchlistOutputData = new GetWatchlistOutputData(username,
                displayName, watchlist);
        getListsPresenter.prepareSuccessView(getWatchlistOutputData);
    }

    /**
     * Switches from list view to account view.
     */
    @Override
    public void switchToAccountView(GetWatchlistInputData getListsInputData) {
        if (userDataAccessObject.getCurrentUsername().equals(getListsInputData.getUsername())) {
            getListsPresenter.switchToPersonalAccountView();
        }
        else {
            getListsPresenter.switchToOtherAccountView();
        }
    }
}
