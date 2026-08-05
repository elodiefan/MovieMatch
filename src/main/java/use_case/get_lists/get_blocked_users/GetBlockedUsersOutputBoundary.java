package use_case.get_lists.get_blocked_users;

import use_case.get_lists.GetListsOutputBoundary;

public interface GetBlockedUsersOutputBoundary extends GetListsOutputBoundary {

    /**
     * Prepares the success view when calling the blocked users use case.
     * @param response the output boundary for the success view.
     */
    void prepareSuccessView(GetBlockedUsersOutputData response);

    /**
     * Switches to the Account View.
     */
    @Override
    void switchToAccountView();
}
