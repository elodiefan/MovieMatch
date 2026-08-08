package use_case.signup;

/**
 * Input data for the Signup Use Case.
 */
public class SignupInputData {

    private final String username;
    private final String displayName;
    private final String password;
    private final String repeatPassword;
    private final String securityQuestion;
    private final String securityAnswer;

    /**
     * Creates the input data needed to attempt a new account signup.
     *
     * @param username the username requested for the new account
     * @param displayName the public display name for the new account
     * @param password the password entered by the user
     * @param repeatPassword the repeated password entered for confirmation
     * @param securityQuestion the selected security question for account recovery
     * @param securityAnswer the answer to the selected security question
     */
    public SignupInputData(String username, String displayName, String password,
                           String repeatPassword, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    /**
     * Returns the username requested for the new account.
     *
     * @return the requested username
     */
    String getUsername() {
        return username;
    }

    /**
     * Returns the public display name for the new account.
     *
     * @return the display name
     */
    String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the password entered by the user.
     *
     * @return the entered password
     */
    String getPassword() {
        return password;
    }

    /**
     * Returns the repeated password entered for confirmation.
     *
     * @return the repeated password
     */
    String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * Returns the selected security question for account recovery.
     *
     * @return the selected security question
     */
    String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Returns the answer to the selected security question.
     *
     * @return the security answer
     */
    String getSecurityAnswer() {
        return securityAnswer;
    }
}
