package interface_adapter.logged_in;

/**
 * The State information representing the logged-in user.
 */
public class LoggedInState {
    private String username = "";

    private String password = "";
    private String passwordError;
    private String deleteAccountError;

    public LoggedInState(LoggedInState copy) {
        username = copy.username;
        password = copy.password;
        passwordError = copy.passwordError;
        deleteAccountError = copy.deleteAccountError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Records why deleting the account failed, so the logged-in view can show it.
     */
    public void deleteAccountError(String deleteAccountError) {
        this.deleteAccountError = deleteAccountError;
    }

    public String getDeleteAccountError() {
        return deleteAccountError;
    }
}
