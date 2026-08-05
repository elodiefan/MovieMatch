package use_case.access_message_chat;

/**
 * The output boundary for the access message chat use case.
 */

public interface AccessMessageChatOutputBoundary {
    /**
     * Prepares the success view for the access message chat use case.
     * @param outputData the output data
     */
    void prepareAccessMessageChatSuccessView(AccessMessageChatOutputData outputData);

    /**
     * Prepares the fail view for the access message chat use case.
     * @param error the output data
     */
    void prepareAccessMessageChatFailView(String error);
}
