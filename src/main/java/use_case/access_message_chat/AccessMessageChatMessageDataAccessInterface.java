package use_case.access_message_chat;

/**
 * Message data access interface for the access message chat use case.
 */

public interface AccessMessageChatMessageDataAccessInterface {
    /**
     * Gets the entire chat history between the two users.
     * @param username the username of the current user
     * @param otherUsername the username of the other user
     * @return chat history as String
     */
    String getNewMessages(String username, String otherUsername);
}
