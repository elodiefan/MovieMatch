package data_access;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import entity.StandardUser;
import entity.User;

/**
 * MongoDB Atlas implementation of {@link UserDataAccessObject}.
 * <p>
 * This is the only class in the project that imports {@code com.mongodb}: all
 * the driver code lives here, so the rest of the app never knows which database
 * is behind the interface.
 * <p>
 * Build one instance in {@code AppBuilder} and share it with every interactor —
 * the underlying {@code MongoClient} is expensive to create and is safe to reuse
 * across the whole app.
 * <p>
 * Connection settings are read from a properties file (default
 * {@value #DEFAULT_PROPERTIES}) holding {@code uri}, {@code database} and
 * {@code collection}. That file is git-ignored because it contains a password.
 */
public class MongoUserDataAccessObject implements UserDataAccessObject {

    /** Default location of the connection settings. */
    public static final String DEFAULT_PROPERTIES = "mongo.properties";

    // Field names exactly as stored in MongoDB. Case matters.
    private static final String USERNAME = "username";
    private static final String DISPLAY_NAME = "displayName";
    private static final String PASSWORD = "password";
    private static final String SECURITY_QUESTION = "securityQuestion";
    private static final String ANSWER = "answer";

    private final MongoClient mongoClient;
    private final MongoCollection<Document> users;

    /** Who is logged in right now. Session state, so it stays in memory. */
    private String currentUsername;

    /**
     * Connects using the settings in {@value #DEFAULT_PROPERTIES}.
     */
    public MongoUserDataAccessObject() {
        this(DEFAULT_PROPERTIES);
    }

    /**
     * Connects using the settings in the given properties file.
     * @param propertiesPath path to the file (e.g. "mongo.properties")
     */
    public MongoUserDataAccessObject(String propertiesPath) {
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
        this.users = database.getCollection(props.getProperty("collection"));
    }

    // ---------- Signup + Login ----------

    @Override
    public boolean existsByName(String username) {
        return users.find(Filters.eq(USERNAME, username)).first() != null;
    }

    /**
     * Same check as {@link #existsByName}, under the name the login use case
     * uses. Signup calls it existsByName and login calls it existsByUsername,
     * so both are provided; there is only one implementation.
     * @param username the account to look for
     * @return true if the account exists
     */
    @Override
    public boolean existsByUsername(String username) {
        return existsByName(username);
    }

    @Override
    public void save(User user) {
        // Insert a brand-new account, or overwrite the fields of an existing one.
        if (existsByName(user.getUsername())) {
            users.updateOne(Filters.eq(USERNAME, user.getUsername()),
                    Updates.combine(
                            Updates.set(DISPLAY_NAME, user.getDisplayName()),
                            Updates.set(PASSWORD, user.getPassword()),
                            Updates.set(SECURITY_QUESTION, user.getSecurityQuestion()),
                            Updates.set(ANSWER, user.getSecurityAnswer())));
        }
        else {
            users.insertOne(new Document(USERNAME, user.getUsername())
                    .append(DISPLAY_NAME, user.getDisplayName())
                    .append(PASSWORD, user.getPassword())
                    .append(SECURITY_QUESTION, user.getSecurityQuestion())
                    .append(ANSWER, user.getSecurityAnswer()));
        }
    }

    @Override
    public User get(String username) {
        final Document doc = users.find(Filters.eq(USERNAME, username)).first();
        if (doc == null) {
            return null;
        }
        return toUser(doc);
    }

    // ---------- Logout (session state only, no database call) ----------

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    // ---------- Change password ----------

    @Override
    public void changePassword(User user) {
        users.updateOne(Filters.eq(USERNAME, user.getUsername()),
                Updates.set(PASSWORD, user.getPassword()));
    }

    // ---------- Reset password (after the security question is answered) ----------

    @Override
    public void changePassword(String username, String newPassword) {
        users.updateOne(Filters.eq(USERNAME, username), Updates.set(PASSWORD, newPassword));
    }

    // ---------- Delete account (after the security question is answered) ----------
    @Override
    public void deleteAccount(User user) {
        users.deleteOne(Filters.eq(USERNAME, user.getUsername()));
    }

    @Override
    public String getCurrentSecurityAnswer() {
        Document doc = users.find(Filters.eq(USERNAME, ANSWER)).first();
        return doc.getString(ANSWER);
    }

    // ---------- Helpers ----------

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
