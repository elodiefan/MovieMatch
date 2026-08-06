package interface_adapter.other_account;

/**
 * The state for the Account View Model.
 */
public class OtherAccountState {
    private String username = "";
    private String displayName = "";
    private boolean blocked;
    private String nextBlockOption = "";
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

    public String getNextBlockOption() {
        return nextBlockOption;
    }

    public void setNextBlockOption(String nextBlockOption) {
        this.nextBlockOption = nextBlockOption;
    }

    public String getViewMessageError() {
        return viewMessageError;
    }

    public void setViewMessageError(String viewMessageError) {
        this.viewMessageError = viewMessageError;
    }
}
