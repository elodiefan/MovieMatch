package use_case.signup;

/**
 * Output data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String username;
    private final String displayName;
    private final boolean useCaseFailed;

    /**
     * Creates the output data for a signup result.
     *
     * @param username the username of the created account
     * @param displayName the display name of the created account
     * @param useCaseFailed whether the signup use case failed
     */
    public SignupOutputData(String username, String displayName, boolean useCaseFailed) {
        this.username = username;
        this.displayName = displayName;
        this.useCaseFailed = useCaseFailed;
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

    /**
     * Returns whether the signup use case failed.
     *
     * @return true if the use case failed; false otherwise
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
