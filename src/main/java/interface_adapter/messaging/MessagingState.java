package interface_adapter.messaging;

import java.util.ArrayList;

public class MessagingState {

    private String username;
    private String otherUsername;

    private ArrayList<String> displayText = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public void setOtherUsername(String otherUsername) {
        this.otherUsername = otherUsername;
    }

    public ArrayList<String> getDisplayText() {
        return displayText;
    }

    public void setDisplayText(ArrayList<String> displayText) {
        this.displayText = displayText;
    }
}
