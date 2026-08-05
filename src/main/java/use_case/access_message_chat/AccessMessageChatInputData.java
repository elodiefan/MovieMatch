package use_case.access_message_chat;

/**
 * The input data for the access message chat use case.
 */

public class AccessMessageChatInputData {

    private final String otherUsername;

    public AccessMessageChatInputData(String otherUsername) {
        this.otherUsername = otherUsername;
    }

    String getOtherUsername() {
        return otherUsername;
    }
}
