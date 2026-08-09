package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import entity.StandardUser;
import entity.User;
import entity.UserLists;

/**
 * MongoDB Atlas implementation of UserDataAccessObject.
 */
public class MongoUserDataAccessObject implements UserDataAccessObject {

    /**
     * Default location of the connection settings.
     */
    public static final String DEFAULT_PROPERTIES = "mongo.properties";

    /**
     * Most accounts one search will return, so a one-letter keyword stays cheap to draw.
     */
    public static final int SEARCH_LIMIT = 50;

    /**
     * Mongo's regex option for ignoring case.
     */
    private static final String CASE_INSENSITIVE = "i";

    // Field names exactly as stored in MongoDB. Case matters.
    private static final String USERNAME = "username";
    private static final String DISPLAY_NAME = "displayName";
    private static final String PASSWORD = "password";
    private static final String SECURITY_QUESTION = "securityQuestion";
    private static final String ANSWER = "answer";
    private static final String WATCHLIST = "watchlist";
    private static final String WATCH_HISTORY = "watchHistory";
    private static final String MEDIA_ID = "mediaId";
    private static final String MEDIA_TYPE = "mediaType";
    private static final String MEDIA_TITLE = "mediaTitle";
    private static final String ADDED_AT = "addedAt";
    private static final String WATCHED_AT = "watchedAt";
    private static final String REVIEWS = "reviews";
    private static final String COMMENTS = "comments";
    private static final String BLOCKED_USERS = "blockedUsers";

    private final MongoClient mongoClient;
    private final MongoCollection<Document> users;

    /**
     * Who is logged in right now.
     */
    private String currentUsername;

    /**
     * Connects using the settings in DEFAULT_PROPERTIES.
     */
    public MongoUserDataAccessObject() {
        this(DEFAULT_PROPERTIES);
    }

    /**
     * Connects using the settings in the given properties file.
     *
     * @param propertiesPath the properties path
     * @throws RuntimeException if the properties file cannot be read
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
     * Same check as #existsByName, under the name the login use case uses.
     *
     * @param username the username
     * @return the exists by username
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
                            Updates.set(ANSWER, user.getAnswer())));
        }
        else {
            users.insertOne(new Document(USERNAME, user.getUsername())
                    .append(DISPLAY_NAME, user.getDisplayName())
                    .append(PASSWORD, user.getPassword())
                    .append(SECURITY_QUESTION, user.getSecurityQuestion())
                    .append(ANSWER, user.getAnswer())
                    .append(WATCHLIST, new ArrayList<Document>())
                    .append(WATCH_HISTORY, new ArrayList<Document>())
                    .append(REVIEWS, new ArrayList<String>())
                    .append(COMMENTS, new ArrayList<String>())
                    .append(BLOCKED_USERS, new ArrayList<String>()));
        }
        ensureUserListFields(user.getUsername());
    }

    @Override
    public void saveUser(String username, String displayName, String password,
                         String securityQuestion, String securityAnswer) {
        if (existsByName(username)) {
            users.updateOne(Filters.eq(USERNAME, username),
                    Updates.combine(
                            Updates.set(DISPLAY_NAME, displayName),
                            Updates.set(PASSWORD, password),
                            Updates.set(SECURITY_QUESTION, securityQuestion),
                            Updates.set(ANSWER, securityAnswer)));
        }
        else {
            users.insertOne(new Document(USERNAME, username)
                    .append(DISPLAY_NAME, displayName)
                    .append(PASSWORD, password)
                    .append(SECURITY_QUESTION, securityQuestion)
                    .append(ANSWER, securityAnswer)
                    .append(WATCHLIST, new ArrayList<Document>())
                    .append(WATCH_HISTORY, new ArrayList<Document>())
                    .append(REVIEWS, new ArrayList<String>())
                    .append(COMMENTS, new ArrayList<String>())
                    .append(BLOCKED_USERS, new ArrayList<String>()));
        }
        ensureUserListFields(username);
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

    // ---------- Reset password (after the security question is answered) ----------

    @Override
    public void changePassword(String username, String newPassword) {
        users.updateOne(Filters.eq(USERNAME, username), Updates.set(PASSWORD, newPassword));
    }

    // ---------- Change display name ----------

    @Override
    public void changeDisplayName(String username, String newDisplayName) {
        users.updateOne(Filters.eq(USERNAME, username), Updates.set(DISPLAY_NAME, newDisplayName));
    }

    // ---------- Change username ----------

    @Override
    public void changeUsername(String username, String newUsername) {
        users.updateOne(Filters.eq(USERNAME, username), Updates.set(USERNAME, newUsername));
    }

    // ---------- Get watchlist ----------

    @Override
    public UserLists getLists(String username) {
        final Document doc = users.find(Filters.eq(USERNAME, username)).first();
        if (doc != null) {
            ensureUserListFields(username);
            final Document updatedDoc = users.find(Filters.eq(USERNAME,
                    username)).first();
            final List<Document> watchlist = getWatchlist(updatedDoc);
            final List<Document> watchHistory = getWatchHistory(updatedDoc);
            final List<String> blockedUsers = getBlockedUsers(updatedDoc);
            return toUserLists(username, watchlist, watchHistory, blockedUsers);
        }
        return new UserLists(username, "", "", "");
    }

    @Override
    public void addToWatchlist(String username, int mediaId, String mediaType,
                               String mediaTitle, String addedAt) {
        ensureUserListFields(username);
        final Document mediaDocument = createMediaListDocument(mediaId,
                mediaType, mediaTitle, ADDED_AT, addedAt);
        replaceMediaListItem(username, WATCHLIST, mediaId, mediaType,
                mediaDocument);
    }

    @Override
    public void addToWatchHistory(String username, int mediaId,
                                  String mediaType, String mediaTitle,
                                  String watchedAt) {
        ensureUserListFields(username);
        final Document mediaDocument = createMediaListDocument(mediaId,
                mediaType, mediaTitle, WATCHED_AT, watchedAt);
        replaceMediaListItem(username, WATCH_HISTORY, mediaId, mediaType,
                mediaDocument);
    }

    private static List<String> getBlockedUsers(Document doc) {
        final List<String> blockedUsers = doc.get(BLOCKED_USERS, List.class);
        return blockedUsers;
    }

    /**
     * Returns the ids of everything on the user's watchlist or watch history.
     *
     * @param username the username
     * @return the find engaged media ids
     */
    @Override
    public Set<Integer> findEngagedMediaIds(String username) {
        final Set<Integer> mediaIds = new LinkedHashSet<>();
        final Document doc = users.find(Filters.eq(USERNAME, username)).first();

        if (doc != null) {
            addMediaIds(getWatchlist(doc), mediaIds);
            addMediaIds(getWatchHistory(doc), mediaIds);
        }
        return mediaIds;
    }

    private static void addMediaIds(List<Document> entries, Set<Integer> into) {
        if (entries != null) {
            for (Document entry : entries) {
                final Integer mediaId = entry.getInteger(MEDIA_ID);
                if (mediaId != null) {
                    into.add(mediaId);
                }
            }
        }
    }

    private static List<Document> getWatchHistory(Document doc) {
        final List<Document> watchHistory = doc.get(WATCH_HISTORY, List.class);
        return watchHistory;
    }

    private static List<Document> getWatchlist(Document doc) {
        final List<Document> watchlist = doc.get(WATCHLIST, List.class);
        return watchlist;
    }

    private void ensureUserListFields(String username) {
        setMissingField(username, WATCHLIST, new ArrayList<Document>());
        setMissingField(username, WATCH_HISTORY, new ArrayList<Document>());
        setMissingField(username, REVIEWS, new ArrayList<String>());
        setMissingField(username, COMMENTS, new ArrayList<String>());
        setMissingField(username, BLOCKED_USERS, new ArrayList<String>());
    }

    private void setMissingField(String username, String fieldName,
                                 List<?> defaultValue) {
        users.updateOne(Filters.and(Filters.eq(USERNAME, username),
                        Filters.exists(fieldName, false)),
                Updates.set(fieldName, defaultValue));
    }

    // ---------- Delete account (after the security question is answered) ----------
    @Override
    public void deleteAccount(User user) {
        users.deleteOne(Filters.eq(USERNAME, user.getUsername()));
    }

    @Override
    public String getCurrentSecurityAnswer() {
        return currentUserField(ANSWER);
    }

    // ---------- Get user profile ----------
    @Override
    public String getDisplayName() {
        return currentUserField(DISPLAY_NAME);
    }

    @Override
    public String getDisplayName(String username) {
        return userField(username, DISPLAY_NAME);
    }

    // ---------- Get security question ----------
    @Override
    public String getSecurityQuestion() {
        return currentUserField(SECURITY_QUESTION);
    }

    // ---------- Search for users ----------

    /**
     * Finds accounts whose username or display name contains the keyword, ignoring case.
     *
     * @param keyword the keyword
     * @return the search
     */
    @Override
    public List<User> search(String keyword) {
        final String literal = Pattern.quote(keyword);
        final Bson query = Filters.or(
                Filters.regex(USERNAME, literal, CASE_INSENSITIVE),
                Filters.regex(DISPLAY_NAME, literal, CASE_INSENSITIVE));

        final List<User> found = new ArrayList<>();
        for (Document doc : users.find(query).limit(SEARCH_LIMIT)) {
            found.add(toUser(doc));
        }
        return found;
    }

    // ---------- Block user ----------
    private List<String> getBlockList(String username) {
        final Document currentUserDoc = users.find(Filters.eq(USERNAME, username)).first();
        final List<String> currentUserBlockList = currentUserDoc.get(BLOCKED_USERS, List.class);
        return currentUserBlockList;
    }

    @Override
    public boolean alreadyBlocked(String otherUsername) {
        final List<String> currentUserBlockList = getBlockList(getCurrentUsername());

        return currentUserBlockList.contains(otherUsername);
    }

    @Override
    public void addToBlockList(String otherUsername) {
        final List<String> currentUserBlockList = getBlockList(getCurrentUsername());

        if (!currentUserBlockList.contains(otherUsername)) {
            currentUserBlockList.add(otherUsername);
            users.updateOne(Filters.eq(USERNAME, getCurrentUsername()), Updates.set(BLOCKED_USERS, currentUserBlockList));
        }
    }

    @Override
    public void removeFromBlockList(String otherUsername) {
        final List<String> currentUserBlockList = getBlockList(getCurrentUsername());

        if (currentUserBlockList.contains(otherUsername)) {
            currentUserBlockList.remove(otherUsername);
            users.updateOne(Filters.eq(USERNAME, getCurrentUsername()), Updates.set(BLOCKED_USERS, currentUserBlockList));
        }
    }

    // ---------- Access chat view ----------
    @Override
    public boolean canMessage(String otherUsername) {
        final List<String> currentUserBlockList = getBlockList(getCurrentUsername());
        final List<String> otherUserBlockList = getBlockList(otherUsername);

        if (currentUserBlockList.contains(otherUsername) || otherUserBlockList.contains(getCurrentUsername())) {
            return true;
        }
        return false;
    }

    // ---------- Helpers ----------

    /**
     * Reads one field off the logged-in user's document.
     *
     * @param field the field
     * @return the current user field
     */
    private String currentUserField(String field) {
        String value = null;
        if (currentUsername != null) {
            final Document doc = users.find(Filters.eq(USERNAME, currentUsername)).first();
            if (doc != null) {
                value = doc.getString(field);
            }
        }
        return value;
    }

    /**
     * Reads one field off the specified user's document.
     *
     * @param username the username
     * @param field the field
     * @return the user field
     */
    private String userField(String username, String field) {
        String value = null;
        if (username != null) {
            final Document doc = users.find(Filters.eq(USERNAME, username)).first();
            if (doc != null) {
                value = doc.getString(field);
            }
        }
        return value;
    }

    /**
     * Turns a MongoDB document into a User entity.
     *
     * @param doc the doc
     * @return the to user
     */
    private User toUser(Document doc) {
        return new StandardUser(
                doc.getString(USERNAME),
                doc.getString(DISPLAY_NAME),
                doc.getString(PASSWORD),
                doc.getString(SECURITY_QUESTION),
                doc.getString(ANSWER));
    }

    private UserLists toUserLists(String username, List<Document> watchlist, List<Document> watchHistory,
                                  List<String> blockedUsers) {
        final String userWatchlist = MongoDataCleaning.convertWatchlistToString(watchlist);
        final String userWatchHistory = MongoDataCleaning.convertWatchHistoryToString(watchHistory);
        final String userBlockedUsers = MongoDataCleaning.convertBlockedUsersToString(blockedUsers);
        return new UserLists(username, userWatchlist, userWatchHistory, userBlockedUsers);
    }

    private Document createMediaListDocument(int mediaId, String mediaType,
                                             String mediaTitle,
                                             String dateField,
                                             String loggedAt) {
        return new Document(MEDIA_ID, mediaId)
                .append(MEDIA_TYPE, mediaType)
                .append(MEDIA_TITLE, mediaTitle)
                .append(dateField, loggedAt);
    }

    private void replaceMediaListItem(String username, String listField,
                                      int mediaId, String mediaType,
                                      Document mediaDocument) {
        users.updateOne(Filters.eq(USERNAME, username),
                Updates.pull(listField, new Document(MEDIA_ID, mediaId)
                        .append(MEDIA_TYPE, mediaType)));
        users.updateOne(Filters.eq(USERNAME, username),
                Updates.push(listField, mediaDocument));
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
