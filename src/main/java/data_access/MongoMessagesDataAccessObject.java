package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import entity.StandardUser;
import entity.User;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class MongoMessagesDataAccessObject {
}
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class MongoMessagesDataAccessObject implements MessageDataAccessObject {

    /** Default location of the connection settings. */
    public static final String DEFAULT_PROPERTIES = "mongo.properties";

    // Field names exactly as stored in MongoDB. Case matters.
    private static final String MEMBERS = "members";
    private static final String HISTORY = "history";
    private static final String SENDER = "sender";
    private static final String BODY = "body";
    private static final String TIMESTAMP = "timestamp";

    private final MongoClient mongoClient;
    private final MongoCollection<Document> messages;

    /** Who is logged in right now. Session state, so it stays in memory. */
    private String currentUsername;

    /**
     * Connects using the settings in {@value #DEFAULT_PROPERTIES}.
     */
    public MongoMessagesDataAccessObject() {
        this(DEFAULT_PROPERTIES);
    }

    /**
     * Connects using the settings in the given properties file.
     * @param propertiesPath path to the file (e.g. "mongo.properties")
     */
    public MongoMessagesDataAccessObject(String propertiesPath) {
        final Properties props = new Properties();
        try (InputStream in = new FileInputStream(propertiesPath)) {
            props.load(in);
        }
        catch (IOException ex) {
            throw new RuntimeException("Could not read " + propertiesPath
                    + ". See the MongoDB guide for the settings this file needs.", ex);
        }
        this.mongoClient = MongoClients.create(props.getProperty("uri"));
        final MongoDatabase database = mongoClient.getDatabase(props.getProperty("database"));
        this.messages = database.getCollection(props.getProperty("collection"));
    }

    // ---------- Helpers ----------

    /**
     * Reads one field off the specified user's document.
     * <p>
     * @param username the specified user
     * @param field the document field to read
     * @return the stored value, or null if nobody is logged in or the account is gone
     */
    private String userField(String username, String field) {
        String value = null;
        if (username != null) {
            final Document doc = messages.find(Filters.eq(MEMBERS, username)).first();
            if (doc != null) {
                value = doc.getString(field);
            }
        }
        return value;
    }

    /** Turns a MongoDB document into a User entity. */
    private User toUser(Document doc) {
        return new StandardUser(
                doc.getString(USERNAME),
                doc.getString(DISPLAY_NAME),
                doc.getString(PASSWORD),
                doc.getString(SECURITY_QUESTION),
                doc.getString(ANSWER));
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
