package use_case.account;

/**
 * Output Data for the Account Use Case.
 */
public class AccountOutputData {

    private final String username;

    public AccountOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
