package entity;

import java.time.LocalDateTime;

/**
 * Represents a direct message between two or more users.
 */

public class Message {

    private final String sender;
    private final String recipient;
    private final String body;
    private final LocalDateTime date;

    public Message(String sender, String recipient, String body, LocalDateTime date) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
