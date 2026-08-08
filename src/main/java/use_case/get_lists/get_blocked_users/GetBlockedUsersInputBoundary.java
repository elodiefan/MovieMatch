package use_case.get_lists.get_blocked_users;

import use_case.get_lists.GetListsInputData;

/**
 * Input Boundary for actions which are related to user's blocked users.
 */

public interface GetBlockedUsersInputBoundary {
    /**
     * Executes the get watchlist use case.
     * @param getListsInputData the input data for the get watchlist use case.
     */
    void execute(GetListsInputData getListsInputData);

    /**
     * Executes the switch to account view use case.
     * @param getListsInputData the input date for the use case.
     */
    void switchToAccountView(GetListsInputData getListsInputData);
}

