package use_case.get_lists.get_blocked_users;

public interface GetBlockedUsersOutputBoundary {

    /**
     * Prepares the success view when calling the blocked users use case.
     * @param response the output boundary for the success view.
     */
    void prepareSuccessView(GetBlockedUsersOutputData response);

    /**
     * Switches to the Account View.
     */
    void switchToAccountView();
}
