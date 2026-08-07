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
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the username currently entered in the signup form.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the display name currently entered in the signup form.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Updates the display name currently entered in the signup form.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the password currently entered in the signup form.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the password currently entered in the signup form.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the repeated password currently entered in the signup form.
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * Updates the repeated password currently entered in the signup form.
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * Returns the selected security question in the signup form.
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Updates the selected security question in the signup form.
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * Returns the security answer currently entered in the signup form.
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * Updates the security answer currently entered in the signup form.
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * Returns the current signup error message.
     */
    public String getSignupError() {
        return signupError;
    }

    /**
     * Updates the current signup error message.
     */
    public void setSignupError(String signupError) {
        this.signupError = signupError;
    }
}
