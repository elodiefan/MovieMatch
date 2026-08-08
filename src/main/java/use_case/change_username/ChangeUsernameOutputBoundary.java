package use_case.change_username;

/**
 * Output boundary for the change username use case.
 */
public interface ChangeUsernameOutputBoundary {

    /**
     * The username was changed successfully.
     * @param outputData the output data for the use case.
     */
    void prepareSuccessView(ChangeUsernameOutputData outputData);

    /**
     * The username was not changed successfully.
     * @param errorMessage the message explaining the failure.
     */
    void prepareFailView(String errorMessage);
}
