package use_case.account;

/**
 * The Input Data for the Account Use Case.
 */
public class AccountInputData {

    private final String username;
    private final String password;

    public AccountInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

}
