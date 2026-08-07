package use_case.change_display_name;

/**
 * Data-access interface for the Reset Password use case. Unlike the
 * security-question step (read-only), this one needs to <em>write</em> the new
 * password back to storage (the in-memory map in tests, or MongoDB in the app).
 */
public interface ChangeDisplayNameUserDataAccessInterface {

    /**
     * Checks whether an account with the given username exists.
     * @param username the username to look up
     * @return true if the account exists
     */
    boolean existsByName(String username);

    /**
     * Updates the display name for the given user.
     * @param username the account to update
     * @param newDisplayName the new display name
     */
    void changeDisplayName(String username, String newDisplayName);
}
