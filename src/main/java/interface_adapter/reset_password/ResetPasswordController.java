package interface_adapter.reset_password;

import use_case.reset_password.ResetPasswordInputBoundary;
import use_case.reset_password.ResetPasswordInputData;

/**
 * Controller for the Reset Password use case. Packages the view's input into
 * use-case input data and forwards it to the interactor.
 */
public class ResetPasswordController {

    private final ResetPasswordInputBoundary interactor;

    public ResetPasswordController(ResetPasswordInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Ask the use case to set a new password for the user.
     * @param username        the account whose password is being changed
     * @param newPassword     the new password
     * @param confirmPassword the new password typed again for confirmation
     */
    public void changePassword(String username, String newPassword, String confirmPassword) {
        interactor.changePassword(new ResetPasswordInputData(username, newPassword, confirmPassword));
    }
}
