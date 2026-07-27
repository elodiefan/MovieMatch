package use_case.delete_account;

/**
 * The input data for the Delete Account Use Case.
 */

public class DeleteAccountInputData {

    private final String username;
    private final String password;
    private final String securityQuestion;
    private final String securityAnswer;

    public DeleteAccountInputData(String username, String password, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    String getUsername() {
        return username;
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
