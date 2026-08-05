package interface_adapter.get_lists;

import use_case.get_lists.GetListsInputData;
import use_case.get_lists.get_blocked_users.GetBlockedUsersInputBoundary;
import use_case.get_lists.get_watch_history.GetWatchHistoryInputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInputBoundary;

/**
 * The controller for the lists view.
 */
public class GetListsController {

    private GetWatchlistInputBoundary getWatchlistUseCaseInteractor;
    private GetWatchHistoryInputBoundary getWatchHistoryInteractor;
    private GetBlockedUsersInputBoundary getBlockedUsersInteractor;


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
     * @param username the username of the user logging in
     * @param displayName the password of the user logging in
     */
    public void executeWatchlistUseCase(String username, String displayName) {
        final GetListsInputData getListsInputData = new GetListsInputData(
                username, displayName);

        getWatchlistUseCaseInteractor.execute(getListsInputData);
    }

    /**
     * Executes the Get Watch History Use Case.
     * @param username the username of the user logging in
     * @param displayName the password of the user logging in
     */
    public void executeWatchHistoryUseCase(String username, String displayName) {
        final GetListsInputData getListsInputData = new GetListsInputData(
                username, displayName);

        getWatchHistoryInteractor.execute(getListsInputData);
    }

    /**
     * Executes the Get Block Users Use Case.
     * @param username the username of the user logging in
     * @param displayName the password of the user logging in
     */
    public void executeBlockUsersUseCase(String username, String displayName) {
        final GetListsInputData getListsInputData = new GetListsInputData(
                username, displayName);

        getBlockedUsersInteractor.execute(getListsInputData);
    }

    /**
     * Executes the return to account view use case.
     */
    public void switchToAccountView() {
        getWatchlistUseCaseInteractor.switchToAccountView();
    }
}
