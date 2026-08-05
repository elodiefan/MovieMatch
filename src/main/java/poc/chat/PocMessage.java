package poc.chat;

/**
 * One chat message, in memory.
 * <p>
 * In the real feature this is what {@code entity.Message} becomes — plain data,
 * no MongoDB types, no Swing types. Notice there is no {@code Document} import
 * here: the database shape stays inside {@link PocChatStore}, so nothing above
 * the data access layer knows Mongo exists.
 */
public class PocMessage {

    private final String sender;
    private final String body;
    private final long sentAt;

    /**
     * Creates a message.
     *
     * @param sender username of whoever sent it
     * @param body the text
     * @param sentAt when it was sent, in milliseconds since the epoch
     */
    public PocMessage(final String sender, final String body, final long sentAt) {
        this.sender = sender;
        this.body = body;
        this.sentAt = sentAt;
    }

    /**
     * Returns who sent this message.
     *
     * @return the sender's username
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * Returns the text of this message.
     *
     * @return the message body
     */
    public String getBody() {
        return this.body;
    }

    /**
     * Returns when this message was sent.
     *
     * @return milliseconds since the epoch
     */
    public long getSentAt() {
        return this.sentAt;
    }
}
