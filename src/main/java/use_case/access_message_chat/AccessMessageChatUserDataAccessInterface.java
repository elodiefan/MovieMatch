package use_case.access_message_chat;

/**
 * Data access interface for the access message chat use case.
 */

public interface AccessMessageChatUserDataAccessInterface {

    /**
     * Checks whether other username has current username blocked or vice versa.
     * @param otherUsername username of other user
     * @return whether user can access chat or not
     */
    boolean canMessage(String otherUsername);

    /**
     * Returns username of current user.
     * @return the current user's username
     */
    String getCurrentUsername();
}
