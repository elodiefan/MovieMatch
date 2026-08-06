package use_case.fetch_chat_history;

/**
 * Message data access interface for the fetch chat history use case.
 */

public interface FetchChatHistoryMessageDataAccessInterface {

    /**
     * Checks whether a chat exists between the two users.
     * @param username the username of the current user
     * @param otherUsername the username of the other user
     * @return whether chat exists between two or not
     */
    boolean chatExists(String username, String otherUsername);

    /**
     * Gets the chat history between the two users.
     * @param username the username of the current user
     * @param otherUsername the username of the other user
     * @return chat history
     */
    String getChatHistory(String username, String otherUsername);
}
