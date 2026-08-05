package poc.chat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

/**
 * Reads and writes chat messages in MongoDB Atlas.
 * <p>
 * <b>This class is the whole point of the proof of concept.</b> There is no
 * server, no WebSocket and no Spring here — Atlas itself is the thing both
 * copies of the app talk to, so it is already the shared relay that a chat
 * feature needs. One person inserts a document, the other person's app finds it.
 * <p>
 * In the real feature this class becomes {@code MongoChatDataAccessObject}, and
 * the two methods below become a {@code ChatDataAccessInterface} that the
 * interactor depends on instead of this class. That interface is what lets the
 * use case be unit tested against an in-memory fake.
 * <p>
 * Writes to a separate {@code poc_messages} collection so that throwing this
 * away later cannot disturb the real one.
 */
public class PocChatStore {

    /** Deliberately not "messages" — this is scratch data. */
    private static final String COLLECTION = "poc_messages";

    // Field names exactly as stored in MongoDB. Case matters.
    private static final String SENDER = "sender";
    private static final String RECIPIENT = "recipient";
    private static final String BODY = "body";
    private static final String SENT_AT = "sentAt";

    private final MongoClient mongoClient;
    private final MongoCollection<Document> messages;

    /**
     * Connects using the same settings file the rest of the app uses.
     *
     * @param propertiesPath path to mongo.properties
     */
    public PocChatStore(final String propertiesPath) {
        final Properties props = new Properties();
        try (InputStream in = new FileInputStream(propertiesPath)) {
            props.load(in);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not read " + propertiesPath
                    + ". Copy the one you already use for the main app.", ex);
        }
        this.mongoClient = MongoClients.create(props.getProperty("uri"));
        final MongoDatabase database = this.mongoClient.getDatabase(props.getProperty("database"));
        this.messages = database.getCollection(COLLECTION);
    }

    /**
     * Stores one message.
     *
     * @param from sender's username
     * @param to recipient's username
     * @param body the text
     */
    public void send(final String from, final String to, final String body) {
        this.messages.insertOne(new Document(SENDER, from)
                .append(RECIPIENT, to)
                .append(BODY, body)
                .append(SENT_AT, System.currentTimeMillis()));
    }

    /**
     * Finds messages in one conversation that arrived after a given moment.
     * <p>
     * A conversation is both directions at once, which is why the filter is an
     * OR of the two sender/recipient pairs. Passing the timestamp of the newest
     * message already on screen means each poll only fetches what is new,
     * rather than re-downloading the whole history every two seconds.
     *
     * @param me one participant
     * @param them the other participant
     * @param since only return messages newer than this (epoch milliseconds)
     * @return the new messages, oldest first
     */
    public List<PocMessage> conversationSince(final String me, final String them, final long since) {
        final Bson betweenTheTwoOfUs = Filters.or(
                Filters.and(Filters.eq(SENDER, me), Filters.eq(RECIPIENT, them)),
                Filters.and(Filters.eq(SENDER, them), Filters.eq(RECIPIENT, me)));
        final Bson query = Filters.and(betweenTheTwoOfUs, Filters.gt(SENT_AT, since));

        final List<PocMessage> found = new ArrayList<>();
        for (Document doc : this.messages.find(query).sort(Sorts.ascending(SENT_AT))) {
            found.add(new PocMessage(
                    doc.getString(SENDER),
                    doc.getString(BODY),
                    doc.getLong(SENT_AT)));
        }
        return found;
    }

    /**
     * Deletes every message in the scratch collection.
     */
    public void deleteEverything() {
        this.messages.deleteMany(new Document());
    }

    /**
     * Closes the connection.
     */
    public void close() {
        this.mongoClient.close();
    }
}
