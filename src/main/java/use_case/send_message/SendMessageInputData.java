package use_case.send_message;

import java.util.Date;

/**
 * The input data for the Send Message Use Case.
 */
public class SendMessageInputData {

    private final String username;
    private final String otherUsername;
    private final String body;
    private final Date date;

    public SendMessageInputData(String username, String otherUsername, String body, Date timestamp) {
        this.username = username;
        this.otherUsername = otherUsername;
        this.body = body;
        this.date = timestamp;
    }

    String getUsername() {
        return username;
    }

    String getOtherUsername() {
        return otherUsername;
    }

    String getBody() {
        return body;
    }

    Date getDate() {
        return date;
    }
}
