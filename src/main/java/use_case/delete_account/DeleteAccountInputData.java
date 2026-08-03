package use_case.delete_account;

/**
 * The input data for the Delete Account Use Case.
 */

public class DeleteAccountInputData {

    private final String username;
    private final String displayName;
    private final String password;
    private final String securityQuestion;
    private final String securityAnswer;

    public DeleteAccountInputData(String username, String displayName, String password, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    String getUsername() {
        return username;
    }

    String getDisplayName() {
        return displayName;
    }

    String getPassword() {
        return password;
    }

    String getSecurityQuestion() {
        return securityQuestion;
    }

    String getSecurityAnswer() {
        return securityAnswer;
    }
}
