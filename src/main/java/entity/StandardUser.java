package entity;

/**
 * Represents a standard user of the app.
 */

public class StandardUser implements User {

    private final String username;
    private final String displayName;
    private final String password;
    private final String securityQuestion;
    private final String securityQuestionAnswer;

    public StandardUser(String username, String displayName, String password,
                        String securityQuestion, String securityQuestionAnswer) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    @Override
    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }
}
