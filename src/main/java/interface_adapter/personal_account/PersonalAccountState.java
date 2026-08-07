package interface_adapter.personal_account;

/**
 * The state for the Account View Model.
 */
public class PersonalAccountState {
    private String username = "";
    private String displayName = "";
    private String password = "";

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
