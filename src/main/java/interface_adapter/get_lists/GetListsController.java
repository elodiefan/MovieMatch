package interface_adapter.get_lists;

import use_case.get_lists.GetListsInputData;
import use_case.get_lists.get_watchlist.GetWatchlistInputBoundary;

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
        final GetListsInputData getListsInputData = new GetListsInputData(
                username, displayName);

        getWatchlistUseCaseInteractor.execute(getListsInputData);
    }

    /**
     * Executes the return to account view use case.
     */
    public void switchToAccountView() {
        getWatchlistUseCaseInteractor.switchToAccountView();
    }
}
