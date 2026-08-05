package interface_adapter.get_lists;

public class GetListsState {

    private String username = "";
    private String displayName = "";

    private String displayText = "";

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}
