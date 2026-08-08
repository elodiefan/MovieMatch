package use_case.get_blocked_users;

/**
 * Input Boundary for actions which are related to user's blocked users.
 */

public interface GetBlockedUsersInputBoundary {
    /**
     * Executes the get watchlist use case.
     * @param getListsInputData the input data for the get watchlist use case.
     */
    void execute(GetBlockedUsersInputData getListsInputData);

    /**
     * Executes the switch to account view use case.
     * @param getListsInputData the input date for the use case.
     */
    void switchToAccountView(GetBlockedUsersInputData getListsInputData);
}
