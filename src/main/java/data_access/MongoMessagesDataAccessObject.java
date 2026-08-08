package data_access;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import entity.Message;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import use_case.access_message_chat.AccessMessageChatMessageDataAccessInterface;
import use_case.fetch_chat_history.FetchChatHistoryMessageDataAccessInterface;
import use_case.send_message.SendMessageMessageDataAccessInterface;

/**
 * Reads and writes chat messages in MongoDB Atlas.
 */
public class MongoMessagesDataAccessObject implements AccessMessageChatMessageDataAccessInterface,
        FetchChatHistoryMessageDataAccessInterface, SendMessageMessageDataAccessInterface {

    /** Default location of the connection settings. */
    public static final String DEFAULT_PROPERTIES = "mongo.properties";

    private static final String COLLECTION = "messages";

    // Field names exactly as stored in MongoDB. Case matters.
    private static final String CHAT_ID = "chat_id";
    private static final String SENDER = "sender";
    private static final String BODY = "body";
    private static final String TIMESTAMP = "timestamp";
    private static final String EMPTY_STRING = "";
    private static final String WHITE_SPACE = " ";

    private final MongoClient mongoClient;
    private final MongoCollection<Document> messages;

    /**
     * Connects using the settings in {@value #DEFAULT_PROPERTIES}.
     */
    public MongoMessagesDataAccessObject() {
        this(DEFAULT_PROPERTIES);
    }

    /**
     * Connects using the same settings file the rest of the app uses.
     * @param propertiesPath path to mongo.properties
     */
    public MongoMessagesDataAccessObject(final String propertiesPath) {
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
     * Checks if a chat exists between the two users.
     * @param username username of current user
     * @param otherUsername username of other user
     * @return whether chat exists between users or not
     */
    public boolean chatExists(String username, String otherUsername) {
        if (messages.find(Filters.or(
                Filters.eq(CHAT_ID, username + WHITE_SPACE + otherUsername),
                Filters.eq(CHAT_ID, otherUsername + WHITE_SPACE + username))).first() != null) {
            return true;
        }
        return false;
    }

    /**
     * Gets the entire chat history between the two users.
     * @param username the username of the current user
     * @param otherUsername the username of the other user
     * @return chat history as String
     */
    public String getNewMessages(String username, String otherUsername) {
        if (chatExists(username, otherUsername)) {
            final List<Document> chats = new ArrayList<>();
            messages.find(Filters.or(
                    Filters.eq(CHAT_ID, username + WHITE_SPACE + otherUsername), Filters.eq(CHAT_ID,
                            otherUsername + WHITE_SPACE + username))).sort(ascending(TIMESTAMP))
                            .forEach(doc -> chats.add(doc));
            return MongoDataCleaning.formatChat(chats);
        }
        return EMPTY_STRING;
    }

    /**
     * Gets the chat history between the two users, starting from the last fetch time.
     * @param username the username of the current user
     * @param otherUsername the username of the other user
     * @param lastFetchTime the time last message displayed was sent
     * @return chat history as String
     */
    public String getNewMessages(String username, String otherUsername, LocalDateTime lastFetchTime) {
        if (chatExists(username, otherUsername)) {
            final List<Document> chats = new ArrayList<>();
            final ZonedDateTime zonedDateTime = lastFetchTime.atZone(ZoneId.of("Canada/Eastern"));
            final Date date = Date.from(zonedDateTime.toInstant());
            messages.find(
                    Filters.and(
                            Filters.or(Filters.eq(CHAT_ID, username + WHITE_SPACE + otherUsername), Filters.eq(
                                    CHAT_ID, otherUsername + WHITE_SPACE + username)),
                            Filters.gte(TIMESTAMP, date)))
                    .sort(Sorts.ascending(TIMESTAMP)).forEach(doc -> chats.add(doc));
            return MongoDataCleaning.formatChat(chats);
        }
        return EMPTY_STRING;
    }

    /**
     * Adds a message log to an existing chatroom.
     * @param message message being sent
     */
    public void addMessage(Message message) {
        final String sender = message.getSender();
        final String recipient = message.getRecipient();
        messages.insertOne(
                new Document(CHAT_ID, sender + WHITE_SPACE + recipient)
                        .append(SENDER, sender)
                        .append(BODY, message.getBody())
                        .append(TIMESTAMP, message.getDate().toString()));
    }

    /**
     * Closes the connection.
     */
    public void close() {
        this.mongoClient.close();
    }







//    /**
//     * Stores one message.
//     *
//     * @param from sender's username
//     * @param to recipient's username
//     * @param body the text
//     */
//    public void send(final String from, final String to, final String body) {
//        this.messages.insertOne(new Document(SENDER, from)
//                .append(RECIPIENT, to)
//                .append(BODY, body)
//                .append(SENT_AT, System.currentTimeMillis()));
//    }
//
//    /**
//     * Finds messages in one conversation that arrived after a given moment.
//     * <p>
//     * A conversation is both directions at once, which is why the filter is an
//     * OR of the two sender/recipient pairs. Passing the timestamp of the newest
//     * message already on screen means each poll only fetches what is new,
//     * rather than re-downloading the whole history every two seconds.
//     *
//     * @param me one participant
//     * @param them the other participant
//     * @param since only return messages newer than this (epoch milliseconds)
//     * @return the new messages, oldest first
//     */
//    public List<PocMessage> conversationSince(final String me, final String them, final long since) {
//        final Bson betweenTheTwoOfUs = Filters.or(
//                Filters.and(Filters.eq(SENDER, me), Filters.eq(RECIPIENT, them)),
//                Filters.and(Filters.eq(SENDER, them), Filters.eq(RECIPIENT, me)));
//        final Bson query = Filters.and(betweenTheTwoOfUs, Filters.gt(SENT_AT, since));
//
//        final List<PocMessage> found = new ArrayList<>();
//        for (Document doc : this.messages.find(query).sort(Sorts.ascending(SENT_AT))) {
//            found.add(new PocMessage(
//                    doc.getString(SENDER),
//                    doc.getString(BODY),
//                    doc.getLong(SENT_AT)));
//        }
//        return found;
//    }
//
//    /**
//     * Deletes every message in the scratch collection.
//     */
//    public void deleteEverything() {
//        this.messages.deleteMany(new Document());
//    }
}
