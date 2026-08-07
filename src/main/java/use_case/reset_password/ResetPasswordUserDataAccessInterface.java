package use_case.reset_password;

/**
 * Data-access interface for the Reset Password use case. Unlike the
 * security-question step (read-only), this one needs to write the new
 * password back to storage (the in-memory map in tests, or MongoDB in the app).
 */
public interface ResetPasswordUserDataAccessInterface {

    /**
     * Checks whether an account with the given username exists.
     */
    boolean existsByName(String username);

    /**
     * Updates the stored password for the given user. All other fields
     * (display name, security question/answer) stay the same.
     */
    void changePassword(String username, String newPassword);
}
