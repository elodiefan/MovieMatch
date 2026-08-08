package use_case.get_blocked_users;

public interface GetBlockedUsersOutputBoundary {

    /**
     * Prepares the success view when calling the blocked users use case.
     * @param response the output boundary for the success view.
     */
    void prepareSuccessView(GetBlockedUsersOutputData response);

    /**
     * Switches to the Personal Account View.
     */
    void switchToPersonalAccountView();

    /**
     * Switches to the Other Account View.
     */
    void switchToOtherAccountView();
}
