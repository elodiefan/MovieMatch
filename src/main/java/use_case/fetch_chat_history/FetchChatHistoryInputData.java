package use_case.fetch_chat_history;

/**
 * The input data for the fetch chat history use case.
 */

public class FetchChatHistoryInputData {

    private final String username;
    private final String otherUsername;

    public FetchChatHistoryInputData(String username, String otherUsername) {
        this.username = username;
        this.otherUsername = otherUsername;
    }

    public String getUsername() {
        return username;
    }

    public String getOtherUsername() {
        return otherUsername;
    }
}
