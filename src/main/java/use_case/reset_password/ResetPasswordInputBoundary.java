package use_case.reset_password;

/**
 * Input boundary for the "Reset Password" use case — the step that runs after a
 * user has proven their identity by answering their security question.
 */
public interface ResetPasswordInputBoundary {

    /**
     * Sets a new password for the given user (after validating it).
     */
    void changePassword(ResetPasswordInputData inputData);
}
