package interface_adapter.messaging;

public class MessagingState {

    private String username;
    private String otherUsername;
    private String displayText;

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

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    /**
     * Appends new text to old text displayed.
     * @param newText new text to append
     */
    public void appendDisplayText(String newText) {
        this.displayText += displayText;
    }
}
