package interface_adapter.delete_account;

import use_case.delete_account.DeleteAccountInputBoundary;
import use_case.delete_account.DeleteAccountInputData;

/**
 * Controller for the Delete Account Use Case
 */

public class DeleteAccountController {
    private final DeleteAccountInputBoundary deleteAccountUseCaseInteractor;

    public DeleteAccountController(DeleteAccountInputBoundary deleteAccountUseCaseInteractor) {
        this.deleteAccountUseCaseInteractor = deleteAccountUseCaseInteractor;
    }

    /**
     * Executes the Delete Account Use Case.
     * @param username the username of the user logged in
     * @param securityQuestion the security question for the user's account
     * @param securityAnswer the security answer for the user's account
     */
    public void execute(String username, String securityQuestion, String securityAnswer) {
        final DeleteAccountInputData deleteAccountInputData = new DeleteAccountInputData(username, securityQuestion, securityAnswer);
        deleteAccountUseCaseInteractor.execute(deleteAccountInputData);
    }

    /**
     * Temporary, later change this to switch to user's profile page.
     */
    public void switchToSignupView() {
        deleteAccountUseCaseInteractor.switchToSignupView();
    }
}
