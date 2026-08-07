package use_case.delete_account;

/**
 * Input Boundary for actions which are related to deleting account.
 */
public interface DeleteAccountInputBoundary {

    /**
     * Executes the delete account use case.
     */
    void execute(DeleteAccountInputData deleteAccountInputData);

    /**
     * Executes the switch to Personal Account view.
     */
    void switchToPersonalAccountView();
}
