package use_case.account;

/**
 * The Input Data for the Account Use Case.
 */
public class AccountInputData {

    private final String username;
    private final String displayName;
    private final String password;

    public AccountInputData(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
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

}
