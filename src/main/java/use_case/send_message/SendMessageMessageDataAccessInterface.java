package use_case.send_message;

import java.util.Date;

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
     * @param username username of current user
     * @param otherUsername username of other user
     * @param message being sent
     * @param date timestamp of when message sent
     */
    void createChat(String username, String otherUsername, String message, Date date);

    /**
     * Adds a message log to an existing chatroom.
     * @param username username of current user
     * @param otherUsername username of other user
     * @param message being sent
     * @param date timestamp of when message sent
     */
    void addMessage(String username, String otherUsername, String message, Date date);
}
