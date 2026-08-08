package use_case.access_message_chat;

/**
 * Input boundary for actions related to accessing message chat.
 */
public interface AccessMessageChatInputBoundary {

    /**
     * Executes the access message chat use case.
     * @param accessMessageChatInputData the input data
     */
    void execute(AccessMessageChatInputData accessMessageChatInputData);
}
