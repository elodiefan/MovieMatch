package use_case.access_message_chat;

/**
 * Data access interface for the access message chat use case.
 */

public interface AccessMessageChatUserDataAccessInterface {

    /**
     * Checks whether other username has current username blocked or vice versa.
     *
     * @param otherUsername the other username
     * @return the can message
     */
    boolean canMessage(String otherUsername);
}
