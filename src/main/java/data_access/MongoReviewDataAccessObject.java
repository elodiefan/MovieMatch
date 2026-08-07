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
import entity.Review;
import use_case.comment.GetUserCommentsReviewDataAccessInterface;
import use_case.review.create_review.CreateReviewDataAccessInterface;
import use_case.review.delete_review.DeleteReviewDataAccessInterface;
import use_case.review.edit_review.EditReviewDataAccessInterface;
import use_case.review.get_media_reviews.GetMediaReviewsDataAccessInterface;
import use_case.review.get_user_reviews.GetUserReviewsDataAccessInterface;
import use_case.review.like_review.LikeReviewDataAccessInterface;
import use_case.review.ReviewDataAccessInterface;
import use_case.review.unlike_review.UnlikeReviewDataAccessInterface;

/** MongoDB data access object for review data. */
public class MongoReviewDataAccessObject implements ReviewDataAccessObject {

    private static final String DEFAULT_PROPERTIES = "mongo.properties";
    private static final String DEFAULT_COLLECTION = "reviews";

    private static final String REVIEW_ID = "reviewId";
    private static final String MEDIA_ID = "mediaId";
    private static final String MEDIA_TYPE = "mediaType";
    private static final String MEDIA_TITLE = "mediaTitle";
    private static final String AUTHOR_USERNAME = "authorUsername";
    private static final String AUTHOR_DISPLAY_NAME = "authorDisplayName";
    private static final String RATING = "rating";
    private static final String REVIEW_TEXT = "reviewText";
    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_AT = "updatedAt";
    private static final String SOURCE = "source";
    private static final String LIKED_BY_USERNAMES = "likedByUsernames";

    private final MongoClient mongoClient;
    private final MongoCollection<Document> reviews;

    /** Connects using the default properties file. */
    public MongoReviewDataAccessObject() {
        this(DEFAULT_PROPERTIES);
    }

    /** Connects using the given properties file. */
    public MongoReviewDataAccessObject(String propertiesPath) {
        final Properties properties = loadProperties(propertiesPath);
        mongoClient = MongoClients.create(properties.getProperty("uri"));

        final MongoDatabase database = mongoClient.getDatabase(
                properties.getProperty("database"));
        reviews = database.getCollection(properties.getProperty(
                "reviewsCollection", DEFAULT_COLLECTION));
    }

    /** Saves a review. */
    public void saveReview(Review review) {
        reviews.replaceOne(Filters.eq(REVIEW_ID, review.getReviewId()),
                toDocument(review), new ReplaceOptions().upsert(true));
    }

    /** Returns whether a review exists. */
    public boolean existsByReviewId(String reviewId) {
        return reviews.find(Filters.eq(REVIEW_ID, reviewId)).first() != null;
    }

    /** Returns a review by id. */
    public Optional<Review> getReviewById(String reviewId) {
        final Document document = reviews.find(Filters.eq(REVIEW_ID, reviewId)).first();
        return Optional.ofNullable(toReview(document));
    }

    /** Returns all reviews written by a user. */
    public List<Review> getReviewsByUsername(String username) {
        final List<Review> matchingReviews = new ArrayList<>();

        for (Document document : reviews.find(Filters.eq(AUTHOR_USERNAME, username))) {
            matchingReviews.add(toReview(document));
        }

        return matchingReviews;
    }

    /** Returns all reviews for one media item. */
    public List<Review> getReviewsByMedia(int mediaId, String mediaType) {
        final List<Review> matchingReviews = new ArrayList<>();

        for (Document document : reviews.find(Filters.and(
                Filters.eq(MEDIA_ID, mediaId),
                Filters.eq(MEDIA_TYPE, mediaType)))) {
            matchingReviews.add(toReview(document));
        }

        return matchingReviews;
    }

    /** Updates an existing review. */
    public boolean editReview(String reviewId, double newRating, String newReviewText,
                              ZonedDateTime newUpdatedAt) {
        reviews.updateOne(Filters.eq(REVIEW_ID, reviewId),
                Updates.combine(
                        Updates.set(RATING, newRating),
                        Updates.set(REVIEW_TEXT, newReviewText),
                        Updates.set(UPDATED_AT, newUpdatedAt.toString())));
        return existsByReviewId(reviewId);
    }

    /** Deletes a review. */
    public boolean deleteReview(String reviewId) {
        return reviews.deleteOne(Filters.eq(REVIEW_ID, reviewId))
                .getDeletedCount() > 0;
    }

    /** Adds a user's like to a review. */
    public boolean likeReview(String reviewId, String username) {
        reviews.updateOne(Filters.eq(REVIEW_ID, reviewId),
                Updates.addToSet(LIKED_BY_USERNAMES, username));
        return existsByReviewId(reviewId);
    }

    /** Removes a user's like from a review. */
    public boolean unlikeReview(String reviewId, String username) {
        reviews.updateOne(Filters.eq(REVIEW_ID, reviewId),
                Updates.pull(LIKED_BY_USERNAMES, username));
        return existsByReviewId(reviewId);
    }

    /** Returns all saved reviews. */
    public List<Review> getAllReviews() {
        final List<Review> allReviews = new ArrayList<>();

        for (Document document : reviews.find()) {
            allReviews.add(toReview(document));
        }

        return allReviews;
    }

    /** Closes the MongoDB connection. */
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

    private Document toDocument(Review review) {
        return new Document(REVIEW_ID, review.getReviewId())
                .append(MEDIA_ID, review.getMediaId())
                .append(MEDIA_TYPE, review.getMediaType())
                .append(MEDIA_TITLE, review.getMediaTitle())
                .append(AUTHOR_USERNAME, review.getAuthorUsername())
                .append(AUTHOR_DISPLAY_NAME, review.getAuthorDisplayName())
                .append(RATING, review.getRating())
                .append(REVIEW_TEXT, review.getReviewText())
                .append(CREATED_AT, review.getCreatedAt().toString())
                .append(UPDATED_AT, review.getUpdatedAt().toString())
                .append(SOURCE, review.getSource())
                .append(LIKED_BY_USERNAMES,
                        new ArrayList<>(review.getLikedByUsernames()));
    }

    private Review toReview(Document document) {
        final Review review;
        if (document == null) {
            review = null;
        }
        else {
            final List<String> likedByUsernames = document.getList(
                    LIKED_BY_USERNAMES, String.class, new ArrayList<>());
            review = new Review(document.getString(REVIEW_ID),
                    document.getInteger(MEDIA_ID),
                    document.getString(MEDIA_TYPE),
                    document.getString(MEDIA_TITLE),
                    document.getString(AUTHOR_USERNAME),
                    document.getString(AUTHOR_DISPLAY_NAME),
                    document.getDouble(RATING),
                    document.getString(REVIEW_TEXT),
                    ZonedDateTime.parse(document.getString(CREATED_AT)),
                    ZonedDateTime.parse(document.getString(UPDATED_AT)),
                    document.getString(SOURCE),
                    new HashSet<>(likedByUsernames));
        }
        return review;
    }
}
