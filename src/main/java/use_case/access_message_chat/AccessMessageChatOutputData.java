package use_case.access_message_chat;

/**
 * Output Data for the access message chat use case.
 */
public class AccessMessageChatOutputData {

    private boolean canViewChat;
    private boolean useCaseFailed;

    public AccessMessageChatOutputData(boolean canViewChat, boolean useCaseFailed) {
        this.canViewChat = canViewChat;
        this.useCaseFailed = useCaseFailed;
    }

    public boolean canViewChat() {
        return canViewChat;
    }
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
