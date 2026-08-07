package use_case.access_message_chat;

/**
 * The output boundary for the access message chat use case.
 */

public interface AccessMessageChatOutputBoundary {
    /**
     * Prepares the success view for the access message chat use case.
     */
    void prepareAccessMessageChatSuccessView(AccessMessageChatOutputData outputData);

    /**
     * Prepares the fail view for the access message chat use case.
     */
    void prepareAccessMessageChatFailView(String error);
}
