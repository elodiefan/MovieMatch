package use_case.reset_password;

/**
 * Data-access interface for the Reset Password use case. Unlike the
 * security-question step (read-only), this one needs to <em>write</em> the new
 * password back to storage (the in-memory map in tests, or MongoDB in the app).
 */
public interface ResetPasswordUserDataAccessInterface {

    /**
     * Checks whether an account with the given username exists.
     * @param username the username to look up
     * @return true if the account exists
     */
    boolean existsByName(String username);

    /**
     * Updates the stored password for the given user. All other fields
     * (display name, security question/answer) stay the same.
     * @param username    the account to update
     * @param newPassword the new password to store
     */
    void changePassword(String username, String newPassword);
}
