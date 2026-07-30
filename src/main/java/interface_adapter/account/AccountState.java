package interface_adapter.account;

/**
 * The state for the Account View Model.
 */
public class AccountState {
    private String username = "";
    private String accountError;
    private String password = "";

    public String getUsername() {
        return username;
    }

    public String getAccountError() {
        return accountError;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountError(String usernameError) {
        this.accountError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
