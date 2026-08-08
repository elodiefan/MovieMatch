package use_case.change_username;

/**
 * Data access interface for the change username use case.
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
