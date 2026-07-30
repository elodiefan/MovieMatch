package use_case.account;

/**
 * Input Boundary for actions which are related to user's account profile.
 */
public interface AccountInputBoundary {

    /**
     * Executes the account use case.
     * @param accountInputData the input data
     */
    void execute(AccountInputData accountInputData);
}
