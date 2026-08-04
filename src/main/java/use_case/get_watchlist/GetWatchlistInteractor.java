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

    /**
     * Switches from list view to account view.
     */
    @Override
    public void switchToAccountView() {
        viewListsPresenter.switchToAccountView();
    }
}
