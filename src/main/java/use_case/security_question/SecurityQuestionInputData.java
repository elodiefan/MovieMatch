package use_case.security_question;

/**
 * Input data for the Security Question use case.
 * A plain, immutable carrier of the two things the interactor needs: which
 * user is trying to recover their account, and what answer they typed. When
 * we are only loading the question (not verifying yet), {@code securityAnswer}
 * can be an empty string.
 */
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
