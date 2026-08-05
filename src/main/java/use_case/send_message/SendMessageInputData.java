package use_case.send_message;

import java.util.Date;

/**
 * The input data for the Send Message Use Case.
 */
public class SendMessageInputData {

    private final String username;
    private final String otherUsername;
    private final String message;
    private final Date date;

    public SendMessageInputData(String username, String otherUsername, String message, Date timestamp) {
        this.username = username;
        this.otherUsername = otherUsername;
        this.message = message;
        this.date = timestamp;
    }

    String getUsername() {
        return username;
    }

    String getOtherUsername() {
        return otherUsername;
    }

    String getMessage() {
        return message;
    }

    Date getDate() {
        return date;
    }
}
