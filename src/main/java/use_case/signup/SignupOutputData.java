package use_case.signup;

/**
 * Output data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String username;
    private final String displayName;

    /**
     * Creates the output data for a signup result.
     *
     * @param username the username of the created account
     * @param displayName the display name of the created account
     */
    public SignupOutputData(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    /**
     * Returns the username of the created account.
     *
     * @return the created account's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the display name of the created account.
     *
     * @return the created account's display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
