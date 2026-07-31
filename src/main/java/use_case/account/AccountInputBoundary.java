package use_case.account;

/**
 * Input Boundary for actions which are related to user's account profile.
 */
public interface AccountInputBoundary {

    /**
     * Executes the switch to reviews view use case.
     */
    void switchToReviewsView();

    /**
     * Executes the switch to log out view use case.
     */
    void switchToLogOutConfirmView();

    /**
     * Executes the switch to reset password view use case.
     */
    void switchToResetPasswordView();

    /**
     * Executes the switch to delete account view use case.
     */
    void switchToDeleteAccountView();
}
