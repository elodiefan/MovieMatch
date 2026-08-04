package interface_adapter.view_lists;

import use_case.get_watchlist.GetWatchlistInputBoundary;

/**
 * The controller for the lists view.
 */
public class ViewListsController {

    private final GetWatchlistInputBoundary getWatchlistUseCaseInteractor;

    public ViewListsController(GetWatchlistInputBoundary getWatchlistUseCaseInteractor) {
        this.getWatchlistUseCaseInteractor = getWatchlistUseCaseInteractor;
    }

    /**
     * Executes the return to account view use case.
     */
    public void switchToAccountView() {
        getWatchlistUseCaseInteractor.switchToAccountView();
    }
}
