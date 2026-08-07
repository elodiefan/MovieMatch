package use_case.fetch_chat_history;

import java.time.LocalDateTime;

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
     * Gets the entire chat history between the two users.
     * @param username the username of the current user
     * @param otherUsername the username of the other user
     * @return chat history as String
     */
    String getNewMessages(String username, String otherUsername);

    /**
     * Gets the chat history between the two users, starting from the last fetch time.
     * @param username the username of the current user
     * @param otherUsername the username of the other user
     * @param lastFetchTime the time last message displayed was sent
     * @return chat history as String
     */
    String getNewMessages(String username, String otherUsername, LocalDateTime lastFetchTime);
}
