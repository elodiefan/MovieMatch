package use_case.change_display_name;

/**
 * Data access interface for the change username use case.
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
