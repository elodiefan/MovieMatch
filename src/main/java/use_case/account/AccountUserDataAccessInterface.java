package use_case.account;

import entity.User;

/**
 * DAO for the Account Use Case.
 */
public interface AccountUserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Saves the user.
     * @param user the user to save
     */
    void save(User user);

    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username
     */
    User get(String username);

    /**
     * Returns the username of the current user.
     * @return the current username.
     */
    String getCurrentUsername();

    /**
     * Sets the username of the current user.
     * @param username the username to set it to
     */
    void setCurrentUsername(String username);

}
