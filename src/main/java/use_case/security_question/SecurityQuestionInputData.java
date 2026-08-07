package use_case.security_question;

/** Input data for the Security Question use case. */
public class SecurityQuestionInputData {

    private final String username;
    private final String securityAnswer;

    public SecurityQuestionInputData(String username, String securityAnswer) {
        this.username = username;
        this.securityAnswer = securityAnswer;
    }

    String getUsername() {
        return username;
    }

    String getSecurityAnswer() {
        return securityAnswer;
    }
}
