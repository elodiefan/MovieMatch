package use_case.account;

/**
 * Output Data for the Account Use Case.
 */
public class AccountOutputData {

    private final String username;
    private final String secuirtyQuestion;

    public AccountOutputData(String username, String secuirtyQuestion) {
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
