package entity;

import java.util.Date;

/**
 * Represents a direct message between two or more users.
 */

public class Message {

    private String sender;
    private String recipient;
    private String body;
    private Date date;

    public Message(String sender, String recipient, String body, Date date) {
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

    public Date getDate() {
        return date;
    }
}
