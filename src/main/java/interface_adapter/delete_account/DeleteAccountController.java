package interface_adapter.delete_account;

import use_case.comment.unlike_comment.delete_account.DeleteAccountInputBoundary;
import use_case.comment.unlike_comment.delete_account.DeleteAccountInputData;

/**
 * Controller for the Delete Account Use Case.
 */

public class DeleteAccountController {
    private final DeleteAccountInputBoundary deleteAccountUseCaseInteractor;

    public DeleteAccountController(DeleteAccountInputBoundary deleteAccountUseCaseInteractor) {
        this.deleteAccountUseCaseInteractor = deleteAccountUseCaseInteractor;
    }

    /**
     * Executes the Delete Account Use Case.
     */
    public void execute(String username, String displayName, String password, String securityQuestion,
                        String securityAnswer) {
        final DeleteAccountInputData deleteAccountInputData = new DeleteAccountInputData(username, displayName,
                password, securityQuestion, securityAnswer);
        deleteAccountUseCaseInteractor.execute(deleteAccountInputData);
    }

    /**
     * Switches view back to user's account page.
     */
    public void switchToPersonalAccountView() {
        deleteAccountUseCaseInteractor.switchToPersonalAccountView();
    }
}