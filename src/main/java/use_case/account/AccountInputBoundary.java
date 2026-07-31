package use_case.account;

/**
 * Input Boundary for actions which are related to user's account profile.
 */
public interface AccountInputBoundary {

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();

    /**
     * Executes the switch to reset password view use case.
     */
    void switchToResetPasswordView();

    /**
     * Executes the switch to delete account view use case.
     */
    void switchToDeleteAccountView();
}
