package use_case.fetch_chat_history;

import java.time.LocalDateTime;

/**
 * The input data for the fetch chat history use case.
 */

public class FetchChatHistoryInputData {

    private final String username;
    private final String otherUsername;
    private final StringBuilder prevMessages;
    private final LocalDateTime date;

    public FetchChatHistoryInputData(String username, String otherUsername, StringBuilder prevMessages,
                                     LocalDateTime date) {
        this.username = username;
        this.otherUsername = otherUsername;
        this.prevMessages = prevMessages;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public StringBuilder getPrevMessages() {
        return prevMessages;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
