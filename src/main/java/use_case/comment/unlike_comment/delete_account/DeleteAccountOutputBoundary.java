package use_case.comment.unlike_comment.delete_account;

/**
 * The output boundary for the Delete Account Use Case.
 */

public interface DeleteAccountOutputBoundary {
    /**
     * Prepares the success view for the Delete Account Use Case.
     */
    void prepareSuccessView(DeleteAccountOutputData outputData);

    /**
     * Prepares the failure view for the Delete Account Use Case.
     */
    void prepareFailView(String errorMessage);

    /**
     * Swtiches to the Account View.
     */
    void switchToPersonalAccountView();
}
