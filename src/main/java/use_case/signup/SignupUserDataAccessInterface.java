package use_case.signup;

import entity.User;

/**
 * Data access interface for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {

    /**
     * Checks whether an account already exists with the given username.
     */
    boolean existsByUsername(String username);

    /**
     * Saves a newly created user account.
     */
    void save(User user);
}
