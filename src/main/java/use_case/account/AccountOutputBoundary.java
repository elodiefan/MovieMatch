package use_case.account;

/**
 * The output boundary for the Account Use Case.
 */
public interface AccountOutputBoundary {

//    /**
//     * Switches to the Reviews View.
//     */
//    void switchToReviewsView();
//
//    /**
//     * Switches to the Log Out Confirm View.
//     */
//    void switchToLogOutConfirmView();

    /**
     * Switches to the Reset Password View.
     */
    void switchToResetPasswordView();

    /**
     * Switches to Delete Account View.
     * @param accountOutputData output data for account use case
     */
    void switchToDeleteAccountView(AccountOutputData accountOutputData);
}
