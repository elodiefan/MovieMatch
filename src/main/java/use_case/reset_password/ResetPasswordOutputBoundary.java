package use_case.reset_password;

/** Output boundary for the Reset Password use case. */
public interface ResetPasswordOutputBoundary {

    /** The password was changed successfully. */
    void prepareSuccessView(ResetPasswordOutputData outputData);

    /** The change failed validation (empty password, or the two entries differ). */
    void prepareFailView(String errorMessage);
}
