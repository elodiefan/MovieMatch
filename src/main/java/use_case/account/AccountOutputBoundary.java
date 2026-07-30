package use_case.account;

/**
 * The output boundary for the Account Use Case.
 */
public interface AccountOutputBoundary {
    /**
     * Prepares the success view for the Account Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AccountOutputData outputData);

    /**
     * Prepares the failure view for the Account Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
