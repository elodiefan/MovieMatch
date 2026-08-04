package interface_adapter.view_lists;

import use_case.get_watchlist.GetWatchlistInputBoundary;
import use_case.get_watchlist.GetWatchlistInputData;

/**
 * The controller for the lists view.
 */
public class GetListsController {

    private final GetWatchlistInputBoundary getWatchlistUseCaseInteractor;

    public GetListsController(GetWatchlistInputBoundary getWatchlistUseCaseInteractor) {
        this.getWatchlistUseCaseInteractor = getWatchlistUseCaseInteractor;
    }

    /**
     * Executes the Get Watchlist Use Case.
     * @param username the username of the user logging in
     * @param displayName the password of the user logging in
     */
    public void execute(String username, String displayName) {
        final GetWatchlistInputData getWatchlistInputData = new GetWatchlistInputData(
                username, displayName);

        getWatchlistUseCaseInteractor.execute(getWatchlistInputData);
    }

    /**
     * Executes the return to account view use case.
     */
    public void switchToAccountView() {
        getWatchlistUseCaseInteractor.switchToAccountView();
    }
}
