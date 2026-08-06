package use_case.access_message_chat;

/**
 * Message data access interface for the access message chat use case.
 */

public interface AccessMessageChatMessageDataAccessInterface {

    /**
     * Gets the messages for the chat between two users.
     * @param username username for current user
     * @param otherUsername username for other user
     * @return messages as a string
     */
    String getChatHistory(String username, String otherUsername);
}
