package interface_adapter.other_account;

/** The state for the Account View Model. */
public class OtherAccountState {
    private String username = "";
    private String displayName = "";
    private boolean blocked;
    private String viewMessageError;

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getViewMessageError() {
        return viewMessageError;
    }

    public void setViewMessageError(String viewMessageError) {
        this.viewMessageError = viewMessageError;
    }
}
