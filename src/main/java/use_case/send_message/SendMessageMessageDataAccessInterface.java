package use_case.send_message;

import entity.Message;

public interface SendMessageMessageDataAccessInterface {

    /**
     * Adds a message log to an existing chatroom.
     * @param message message being sent
     */
    void addMessage(Message message);
}
