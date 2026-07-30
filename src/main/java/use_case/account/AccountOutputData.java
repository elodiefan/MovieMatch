package use_case.account;

/**
 * Output Data for the Account Use Case.
 */
public class AccountOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public AccountOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
