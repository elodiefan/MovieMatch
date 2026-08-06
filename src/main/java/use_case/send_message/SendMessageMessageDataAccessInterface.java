package use_case.send_message;

import entity.Message;

public interface SendMessageMessageDataAccessInterface {

    /**
     * Returns whether a chatroom with the 2 user already exists or not.
     * @param username username of current user
     * @param otherUsername username of other user
     * @return whether chatrrom exists
     */
    boolean chatExists(String username, String otherUsername);

    /**
     * Creates a new chatroom to add message logs.
     * @param message message being sent and added to new chat
     */
    void createChat(Message message);

    /**
     * Adds a message log to an existing chatroom.
     * @param message message being sent
     */
    void addMessage(Message message);
}
