package interface_adapter.get_lists;

public class GetListsState {

    private String username = "";
    private String displayName = "";

    private String displayText = "";

    /**
     * Which list is on screen, so the heading can name it. All three lists share
     * this one state, so without it every list is headed the same way.
     */
    private String listLabel = GetListsViewModel.LIST_LABEL;

    public String getListLabel() {
        return listLabel;
    }

    public void setListLabel(String listLabel) {
        this.listLabel = listLabel;
    }

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
