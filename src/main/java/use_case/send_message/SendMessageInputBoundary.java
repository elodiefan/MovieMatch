package use_case.send_message;

/**
 * Input boundary for actions related to sending messages.
 */

public interface SendMessageInputBoundary {

    /**
     * Executes the send message use case.
     * @param sendMessageInputData the input data
     */
    void execute(SendMessageInputData sendMessageInputData);
}
