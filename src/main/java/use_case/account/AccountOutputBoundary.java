package use_case.account;

/**
 * The output boundary for the Account Use Case.
 */
public interface AccountOutputBoundary {

    /**
     * Switches to the Login View.
     */
    void switchToLoginView();

    /**
     * Switches to the Reset Password View.
     */
    void switchToResetPasswordView();

    /**
     * Switches to Delete Account View.
     */
    void switchToDeleteAccountView();
}
