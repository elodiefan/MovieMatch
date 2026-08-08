package data_access;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Updates;
import entity.Comment;
import use_case.comment.CommentDataAccessInterface;
import use_case.comment.create_comment.CreateCommentDataAccessInterface;
import use_case.comment.delete_comment.DeleteCommentDataAccessInterface;
import use_case.comment.get_review_comments.GetReviewCommentsDataAccessInterface;
import use_case.comment.get_user_comments.GetUserCommentsDataAccessInterface;
import use_case.comment.like_comment.LikeCommentDataAccessInterface;
import use_case.comment.unlike_comment.UnlikeCommentDataAccessInterface;

/**
 * MongoDB data access object for comments on reviews.
 */
public class MongoCommentDataAccessObject implements CommentDataAccessObject{

    private static final String DEFAULT_PROPERTIES = "mongo.properties";
    private static final String DEFAULT_COLLECTION = "comments";

    private static final String COMMENT_ID = "commentId";
    private static final String REVIEW_ID = "reviewId";
    private static final String PARENT_COMMENT_ID = "parentCommentId";
    private static final String AUTHOR_USERNAME = "authorUsername";
    private static final String AUTHOR_DISPLAY_NAME = "authorDisplayName";
    private static final String COMMENT_TEXT = "commentText";
    private static final String CREATED_AT = "createdAt";
    private static final String LIKED_BY_USERNAMES = "likedByUsernames";

    private final MongoClient mongoClient;
    private final MongoCollection<Document> comments;

    /**
     * Connects using the default properties file.
     */
    public MongoCommentDataAccessObject() {
        this(DEFAULT_PROPERTIES);
    }

    /**
     * Connects using the given properties file.
     * @param propertiesPath the path to the MongoDB properties file
     */
    public MongoCommentDataAccessObject(String propertiesPath) {
        final Properties properties = loadProperties(propertiesPath);
        mongoClient = MongoClients.create(properties.getProperty("uri"));

        final MongoDatabase database = mongoClient.getDatabase(
                properties.getProperty("database"));
        comments = database.getCollection(properties.getProperty(
                "commentsCollection", DEFAULT_COLLECTION));
    }

    /**
     * Saves a comment.
     * @param comment the comment to save
     */
    public void saveComment(Comment comment) {
        comments.replaceOne(Filters.eq(COMMENT_ID, comment.getCommentId()),
                toDocument(comment), new ReplaceOptions().upsert(true));
    }

    /**
     * Returns whether a comment exists.
     * @param commentId the comment id to check
     * @return true if a comment with this id exists
     */
    public boolean existsByCommentId(String commentId) {
        return comments.find(Filters.eq(COMMENT_ID, commentId)).first() != null;
    }

    /**
     * Returns a comment by id.
     * @param commentId the comment id to search for
     * @return the comment, if it exists
     */
    public Optional<Comment> getCommentById(String commentId) {
        final Document document = comments.find(Filters.eq(COMMENT_ID, commentId)).first();
        return Optional.ofNullable(toComment(document));
    }

    /**
     * Returns all comments on a review.
     * @param reviewId the review id
     * @return the matching comments
     */
    public List<Comment> getCommentsByReviewId(String reviewId) {
        final List<Comment> matchingComments = new ArrayList<>();

        for (Document document : comments.find(Filters.eq(REVIEW_ID, reviewId))) {
            matchingComments.add(toComment(document));
        }

        return matchingComments;
    }

    /**
     * Returns all comments written by a user.
     * @param username the author's username
     * @return the matching comments
     */
    public List<Comment> getCommentsByUsername(String username) {
        final List<Comment> matchingComments = new ArrayList<>();

        for (Document document : comments.find(Filters.eq(AUTHOR_USERNAME, username))) {
            matchingComments.add(toComment(document));
        }

        return matchingComments;
    }

    /**
     * Returns all replies to a parent comment.
     * @param parentCommentId the parent comment id
     * @return the matching replies
     */
    public List<Comment> getRepliesByParentCommentId(String parentCommentId) {
        final List<Comment> matchingReplies = new ArrayList<>();

        for (Document document : comments.find(Filters.eq(PARENT_COMMENT_ID, parentCommentId))) {
            matchingReplies.add(toComment(document));
        }

        return matchingReplies;
    }

    /**
     * Updates an existing comment.
     * @param commentId the comment id
     * @param newCommentText the updated comment text
     * @return true if the comment was updated
     */
    public boolean editComment(String commentId, String newCommentText) {
        comments.updateOne(Filters.eq(COMMENT_ID, commentId),
                Updates.set(COMMENT_TEXT, newCommentText));
        return existsByCommentId(commentId);
    }

    /**
     * Deletes a comment.
     * @param commentId the comment id
     * @return true if the comment was deleted
     */
    public boolean deleteComment(String commentId) {
        return comments.deleteOne(Filters.eq(COMMENT_ID, commentId))
                .getDeletedCount() > 0;
    }

    /**
     * Adds a user's like to a comment.
     * @param commentId the comment id
     * @param username the username liking the comment
     * @return true if the comment exists
     */
    public boolean likeComment(String commentId, String username) {
        comments.updateOne(Filters.eq(COMMENT_ID, commentId),
                Updates.addToSet(LIKED_BY_USERNAMES, username));
        return existsByCommentId(commentId);
    }

    /**
     * Removes a user's like from a comment.
     * @param commentId the comment id
     * @param username the username unliking the comment
     * @return true if the comment exists
     */
    public boolean unlikeComment(String commentId, String username) {
        comments.updateOne(Filters.eq(COMMENT_ID, commentId),
                Updates.pull(LIKED_BY_USERNAMES, username));
        return existsByCommentId(commentId);
    }

    /**
     * Returns all saved comments.
     * @return all comments
     */
    public List<Comment> getAllComments() {
        final List<Comment> allComments = new ArrayList<>();

        for (Document document : comments.find()) {
            allComments.add(toComment(document));
        }

        return allComments;
    }

    @Override
    public void close() {
        mongoClient.close();
    }

    private Properties loadProperties(String propertiesPath) {
        final Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(propertiesPath)) {
            properties.load(inputStream);
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read " + propertiesPath + ".", exception);
        }
        return properties;
    }

    private Document toDocument(Comment comment) {
        return new Document(COMMENT_ID, comment.getCommentId())
                .append(REVIEW_ID, comment.getReviewId())
                .append(PARENT_COMMENT_ID, comment.getParentCommentId())
                .append(AUTHOR_USERNAME, comment.getAuthorUsername())
                .append(AUTHOR_DISPLAY_NAME, comment.getAuthorDisplayName())
                .append(COMMENT_TEXT, comment.getCommentText())
                .append(CREATED_AT, comment.getCreatedAt().toString())
                .append(LIKED_BY_USERNAMES,
                        new ArrayList<>(comment.getLikedByUsernames()));
    }

    private Comment toComment(Document document) {
        final Comment comment;
        if (document == null) {
            comment = null;
        }
        else {
            final List<String> likedByUsernames = document.getList(
                    LIKED_BY_USERNAMES, String.class, new ArrayList<>());
            comment = new Comment(document.getString(COMMENT_ID),
                    document.getString(REVIEW_ID),
                    document.getString(PARENT_COMMENT_ID),
                    document.getString(AUTHOR_USERNAME),
                    document.getString(AUTHOR_DISPLAY_NAME),
                    document.getString(COMMENT_TEXT),
                    ZonedDateTime.parse(document.getString(CREATED_AT)),
                    new HashSet<>(likedByUsernames));
        }
        return comment;
    }
}
