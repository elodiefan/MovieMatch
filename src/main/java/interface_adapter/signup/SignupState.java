package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String username = "";
    private String usernameError;
    private String displayName = "";
    private String displayNameError;
    private String password = "";
    private String passwordError;
    private String repeatPassword = "";
    private String repeatPasswordError;
    private String securityQuestion = "";
    private String securityQuestionError;
    private String securityAnswer = "";
    private String securityAnswerError;

    public String getUsername() {
        return username;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getDisplayName() { return displayName; }

    public String getDisplayNameError() { return displayNameError; }

    public String getPassword() {
        return password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityQuestionError() {
        return securityQuestionError;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public String getSecurityAnswerError() {
        return securityAnswerError;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDisplayNameError(String displayNameError) {
        this.displayNameError = displayNameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void setRepeatPasswordError(String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityQuestionError(String securityQuestionError) {
        this.securityQuestionError = securityQuestionError;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setSecurityAnswerError(String securityAnswerError) {
        this.securityAnswerError = securityAnswerError;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + username + '\''
                + "displayName='" + displayName + '\''
                + ", password='" + password + '\''
                + ", repeatPassword='" + repeatPassword + '\''
                + "securityAnswer='" + securityAnswer + '\''
                + '}';
    }
}
