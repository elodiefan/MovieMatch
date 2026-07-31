package interface_adapter.logout;

/**
 * The state for the Logout View.
 */
public class LogoutState {

    private String username;

    public LogoutState() {
        this.username = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
