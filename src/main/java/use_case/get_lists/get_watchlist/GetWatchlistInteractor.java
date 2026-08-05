package use_case.get_lists.get_watchlist;

import use_case.get_lists.GetListsInputData;
import use_case.get_lists.GetListsUserDataAccessInterface;

/**
 * The Watchlist View Interactor.
 */

public class GetWatchlistInteractor implements GetWatchlistInputBoundary {

    private final GetListsUserDataAccessInterface userDataAccessObject;
    private final GetWatchListOutputBoundary getListsPresenter;

    public GetWatchlistInteractor(GetListsUserDataAccessInterface userDataAccessInterface,
                                  GetWatchListOutputBoundary getWatchListOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.getListsPresenter = getWatchListOutputBoundary;
    }

    @Override
    public void execute(GetListsInputData getListsInputData) {
        final String username = getListsInputData.getUsername();
        final String displayName = getListsInputData.getDislayName();
        final String watchlist = userDataAccessObject.getLists(username).getWatchlist();
        final GetWatchlistOutputData getWatchlistOutputData = new GetWatchlistOutputData(username,
                displayName, watchlist);
        getListsPresenter.prepareSuccessView(getWatchlistOutputData);
    }

    /**
     * Switches from list view to account view.
     */
    @Override
    public void switchToAccountView() {
        getListsPresenter.switchToAccountView();
    }
}
