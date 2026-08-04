package use_case.get_watchlist;

/**
 * The List View Interactor.
 */

public class GetWatchlistInteractor implements GetWatchlistInputBoundary {

    private final GetWatchlistUserDataAccessInterface userDataAccessObject;
    private final GetWatchlistOutputBoundary viewListsPresenter;

    public GetWatchlistInteractor(GetWatchlistUserDataAccessInterface userDataAccessInterface,
                                  GetWatchlistOutputBoundary getWatchlistOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.viewListsPresenter = getWatchlistOutputBoundary;
    }

    @Override
    public void execute(GetWatchlistInputData getWatchlistInputData) {
        final String username = getWatchlistInputData.getUsername();
        final String displayName = getWatchlistInputData.getDislayName();
        final String watchlist = userDataAccessObject.getLists(username).getWatchlist();
        final GetWatchlistOutputData getWatchlistOutputData = new GetWatchlistOutputData(username,
                displayName, watchlist);
        viewListsPresenter.prepareSuccessView(getWatchlistOutputData);
    }

    /**
     * Switches from list view to account view.
     */
    @Override
    public void switchToAccountView() {
        viewListsPresenter.switchToAccountView();
    }
}
