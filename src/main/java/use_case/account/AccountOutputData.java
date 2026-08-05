package use_case.account;

/**
 * Output Data for the Account Use Case.
 */
public class AccountOutputData {

    private final String username;
    private final String displayName;
    private final String securityQuestion;

    public AccountOutputData(String username, String displayName, String securityQuestion) {
        this.username = username;
        this.displayName = displayName;
        this.securityQuestion = securityQuestion;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

}
