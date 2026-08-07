package use_case.change_username;

/**
 * Data-access interface for the Reset Password use case. Unlike the
 * security-question step (read-only), this one needs to <em>write</em> the new
 * password back to storage (the in-memory map in tests, or MongoDB in the app).
 */
public interface ChangeUsernameUserDataAccessInterface {

    /**
     * Checks whether an account with the given username exists.
     * @param username the username to look up
     * @return true if the account exists
     */
    boolean existsByName(String username);

    /**
     * Updates the username for the given user.
     * @param username the account to update
     * @param newUsername the new user name
     */
    void changeUsername(String username, String newUsername);
}
