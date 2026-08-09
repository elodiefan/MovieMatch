package use_case.change_display_name;

/**
 * Output boundary for the change display name use case.
 */
public interface ChangeDisplayNameOutputBoundary {

    /**
     * The display name was changed successfully.
     * @param outputData the output data for the use case.
     */
    void prepareSuccessView(ChangeDisplayNameOutputData outputData);

    /**
     * The display name was not changed successfully (too long, user doesn't exist).
     * @param errorMessage the message explaining the failure.
     */
    void prepareFailView(String errorMessage);
}
