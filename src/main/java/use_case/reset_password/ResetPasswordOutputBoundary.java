package use_case.reset_password;

/**
 * Output boundary for the Reset Password use case.
 */
public interface ResetPasswordOutputBoundary {

    /**
     * The password was changed successfully.
     * @param outputData the successful result (carries the username)
     */
    void prepareSuccessView(ResetPasswordOutputData outputData);

    /**
     * The change failed validation (empty password, or the two entries differ).
     * @param errorMessage a human-readable explanation
     */
    void prepareFailView(String errorMessage);
}
