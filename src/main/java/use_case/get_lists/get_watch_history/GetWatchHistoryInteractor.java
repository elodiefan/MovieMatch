package use_case.get_lists.get_watch_history;

import use_case.get_lists.GetListsInputData;
import use_case.get_lists.GetListsUserDataAccessInterface;
import use_case.get_lists.get_watchlist.GetWatchListOutputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistOutputData;

/**
 * The Watch History View Interactor.
 */

public class GetWatchHistoryInteractor implements GetWatchlistInputBoundary {

    private final GetListsUserDataAccessInterface userDataAccessObject;
    private final GetWatchListOutputBoundary getListsPresenter;

    public GetWatchHistoryInteractor(GetListsUserDataAccessInterface userDataAccessInterface,
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
    public void switchToAccountView(GetListsInputData getListsInputData) {
        if (userDataAccessObject.getCurrentUsername().equals(getListsInputData.getUsername())) {
            getListsPresenter.switchToPersonalAccountView();
        }
        else {
            getListsPresenter.switchToOtherAccountView();
        }
    }
}
