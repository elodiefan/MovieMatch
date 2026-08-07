package interface_adapter.get_lists;

import use_case.get_blocked_users.GetBlockedUsersInputBoundary;
import use_case.get_blocked_users.GetBlockedUsersInputData;
import use_case.get_watch_history.GetWatchHistoryInputBoundary;
import use_case.get_watch_history.GetWatchHistoryInputData;
import use_case.get_watchlist.GetWatchlistInputBoundary;
import use_case.get_watchlist.GetWatchlistInputData;

/**
 * The controller for the lists view.
 */
public class GetListsController {

    private GetWatchlistInputBoundary getWatchlistUseCaseInteractor;
    private GetWatchHistoryInputBoundary getWatchHistoryInteractor;
    private GetBlockedUsersInputBoundary getBlockedUsersInteractor;

    public GetListsController(GetWatchlistInputBoundary getWatchlistUseCaseInteractor,
                              GetWatchHistoryInputBoundary getWatchHistoryInteractor,
                              GetBlockedUsersInputBoundary getBlockedUsersInteractor) {
        this.getWatchlistUseCaseInteractor = getWatchlistUseCaseInteractor;
        this.getWatchHistoryInteractor = getWatchHistoryInteractor;
        this.getBlockedUsersInteractor = getBlockedUsersInteractor;
    }

    public GetListsController(GetWatchlistInputBoundary getWatchlistUseCaseInteractor) {
        this.getWatchlistUseCaseInteractor = getWatchlistUseCaseInteractor;
    }

    public GetListsController(GetWatchHistoryInputBoundary getWatchHistoryInteractor) {
        this.getWatchHistoryInteractor = getWatchHistoryInteractor;
    }

    public GetListsController(GetBlockedUsersInputBoundary getBlockedUsersInteractor) {
        this.getBlockedUsersInteractor = getBlockedUsersInteractor;
    }

    /**
     * Executes the Get Watchlist Use Case.
     */
    public void executeWatchlistUseCase(String username, String displayName) {
        final GetWatchlistInputData getListsInputData = new GetWatchlistInputData(
                username, displayName);

        getWatchlistUseCaseInteractor.execute(getListsInputData);
    }

    /**
     * Executes the Get Watch History Use Case.
     */
    public void executeWatchHistoryUseCase(String username, String displayName) {
        final GetWatchHistoryInputData getListsInputData = new GetWatchHistoryInputData(
                username, displayName);

        getWatchHistoryInteractor.execute(getListsInputData);
    }

    /**
     * Executes the Get Block Users Use Case.
     */
    public void executeBlockUsersUseCase(String username, String displayName) {
        final GetBlockedUsersInputData getListsInputData = new GetBlockedUsersInputData(
                username, displayName);

        getBlockedUsersInteractor.execute(getListsInputData);
    }

    /**
     * Executes the return to account view use case.
     */
    public void switchToAccountView(String username, String displayName) {
        if (getWatchlistUseCaseInteractor != null) {
            final GetWatchlistInputData getListsInputData =
                    new GetWatchlistInputData(username, displayName);
            getWatchlistUseCaseInteractor.switchToAccountView(getListsInputData);
        }
        else if (getWatchHistoryInteractor != null) {
            final GetWatchHistoryInputData getListsInputData =
                    new GetWatchHistoryInputData(username, displayName);
            getWatchHistoryInteractor.switchToAccountView(getListsInputData);
        }
        else {
            final GetBlockedUsersInputData getListsInputData =
                    new GetBlockedUsersInputData(username, displayName);
            getBlockedUsersInteractor.switchToAccountView(getListsInputData);
        }
    }
    // dont bother switching to account view, call getprofile interactor instead to get that file to make a choice of which profile to view
}
