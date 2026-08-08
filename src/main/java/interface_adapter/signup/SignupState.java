package interface_adapter.signup;

/**
 * State for the Signup View.
 */
public class SignupState {

    private String username = "";
    private String displayName = "";
    private String password = "";
    private String repeatPassword = "";
    private String securityQuestion = "";
    private String securityAnswer = "";
    private String signupError;

    /**
     * Returns the username currently entered in the signup form.
     *
     * @return the entered username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the username currently entered in the signup form.
     *
     * @param username the entered username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the display name currently entered in the signup form.
     *
     * @return the entered display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Updates the display name currently entered in the signup form.
     *
     * @param displayName the entered display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the password currently entered in the signup form.
     *
     * @return the entered password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the password currently entered in the signup form.
     *
     * @param password the entered password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the repeated password currently entered in the signup form.
     *
     * @return the entered repeated password
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * Updates the repeated password currently entered in the signup form.
     *
     * @param repeatPassword the entered repeated password
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * Returns the selected security question in the signup form.
     *
     * @return the selected security question
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Updates the selected security question in the signup form.
     *
     * @param securityQuestion the selected security question
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * Returns the security answer currently entered in the signup form.
     *
     * @return the entered security answer
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * Updates the security answer currently entered in the signup form.
     *
     * @param securityAnswer the entered security answer
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * Returns the current signup error message.
     *
     * @return the signup error message, or null if there is no error
     */
    public String getSignupError() {
        return signupError;
    }

    /**
     * Updates the current signup error message.
     *
     * @param signupError the signup error message
     */
    public void setSignupError(String signupError) {
        this.signupError = signupError;
    }
}
