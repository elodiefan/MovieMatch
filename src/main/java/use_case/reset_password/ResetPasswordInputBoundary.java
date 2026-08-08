package use_case.reset_password;

/**
 * Input boundary for the "Reset Password" use case â€” the step that runs after a
 * user has proven their identity by answering their security question.
 */
public interface ResetPasswordInputBoundary {

    /**
     * Sets a new password for the given user (after validating it).
     * @param inputData the username plus the new password and its confirmation
     */
    void changePassword(ResetPasswordInputData inputData);
}
