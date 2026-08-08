package use_case.access_message_chat;

/**
 * Output Data for the access message chat use case.
 */
public class AccessMessageChatOutputData {

    private boolean canViewChat;
    private boolean useCaseFailed;

    /**
     * Creates output data for accessing a message chat.
     * @param canViewChat whether the chat can be viewed
     * @param useCaseFailed whether the use case failed
     */
    public AccessMessageChatOutputData(boolean canViewChat, boolean useCaseFailed) {
        this.canViewChat = canViewChat;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Returns whether the chat can be viewed.
     * @return true if the chat can be viewed
     */
    public boolean canViewChat() {
        return canViewChat;
    }
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
