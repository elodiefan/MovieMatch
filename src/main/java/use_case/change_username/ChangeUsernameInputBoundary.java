package use_case.change_username;

/**
 * Input boundary for the change username use case.
 */
public interface ChangeUsernameInputBoundary {

    /**
     * Sets a new username for the given user.
     * @param inputData the input data for the use case.
     */
    void changeUsername(ChangeUsernameInputData inputData);
}
