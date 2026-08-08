package use_case.signup;

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
     * @param username the username
     * @param displayName the display name
     * @param password the password
     * @param securityQuestion the security question
     * @param securityAnswer the security answer
     */
    void saveUser(String username, String displayName, String password,
                  String securityQuestion, String securityAnswer);
}
