package use_case.access_message_chat;

/**
 * Output Data for the access message chat use case.
 */
public class AccessMessageChatOutputData {

    private boolean canViewChat;
    private String username;
    private String otherUsername;
    private String displayText;
    private boolean useCaseFailed;

    public AccessMessageChatOutputData(boolean canViewChat, String username, String otherUsername,
                                       String displayText, boolean useCaseFailed) {
        this.canViewChat = canViewChat;
        this.username = username;
        this.otherUsername = otherUsername;
        this.displayText = displayText;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Returns whether current user can chat with other user.
     * @return whether can chat
     */
    public boolean canViewChat() {
        return canViewChat;
    }

    public String getUsername() {
        return username;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public String getDisplayText() {
        return displayText;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
