package use_case.get_lists.get_blocked_users;

import use_case.get_lists.GetListsOutputBoundary;

public interface GetBlockedUsersOutputBoundary extends GetListsOutputBoundary {

    /**
     * Prepares the success view when calling the blocked users use case.
     */
    void prepareSuccessView(GetBlockedUsersOutputData response);

    /**
     * Switches to the Personal Account View.
     */
    @Override
    void switchToPersonalAccountView();

    /**
     * Switches to the Other Account View.
     */
    @Override
    void switchToOtherAccountView();
}
