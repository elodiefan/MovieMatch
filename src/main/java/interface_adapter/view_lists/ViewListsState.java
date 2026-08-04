package interface_adapter.view_lists;

public class ViewListsState {

    private String username = "";
    private String buttonClicked = "";

    public String getUsername() {
        return username;
    }

    public String getButtonClicked() {
        return buttonClicked;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setButtonClicked(String buttonClicked) {
        this.buttonClicked = buttonClicked;
    }
}
