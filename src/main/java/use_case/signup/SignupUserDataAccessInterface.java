package use_case.signup;

import entity.User;

/**
 * Data access interface for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {

    /**
     * Checks whether an account already exists with the given username.
     *
     * @param username the username to check
     * @return true if the username is already taken; false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Saves a newly created user account.
     *
     * @param user the user account to save
     */
    void save(User user);
}
