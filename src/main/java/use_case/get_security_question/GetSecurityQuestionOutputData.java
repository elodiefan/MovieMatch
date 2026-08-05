package use_case.get_security_question;

/**
 * Output Data for the get security question use case.
 */
public class GetSecurityQuestionOutputData {

    private final String username;
    private final String secuirtyQuestion;

    public GetSecurityQuestionOutputData(String username, String secuirtyQuestion) {
        this.username = username;
        this.secuirtyQuestion = secuirtyQuestion;
    }

    public String getUsername() {
        return username;
    }

    public String getSecuirtyQuestion() {
        return secuirtyQuestion;
    }
}
