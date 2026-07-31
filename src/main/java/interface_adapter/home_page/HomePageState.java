package interface_adapter.home_page;

/**
 * The state for the Home Page View Model.
 */

public class HomePageState {
    private String username = "";
    private String displayName = "";
    private String password = "";
    private String securityQuestion = "";
    private String securityAnswer = "";
    private String securityAnswerError = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityAnswerError() {
        return securityAnswerError;
    }

    public void setSecurityAnswerError(String securityAnswerError) {
        this.securityAnswerError = securityAnswerError;
    }
}
