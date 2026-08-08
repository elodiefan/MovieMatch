package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.bson.Document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Updates;
import entity.Review;
import use_case.create_review.CreateReviewDataAccessInterface;
import use_case.delete_review.DeleteReviewDataAccessInterface;
import use_case.edit_review.EditReviewDataAccessInterface;
import use_case.get_media_reviews.GetMediaReviewsDataAccessInterface;
import use_case.get_user_comments.GetUserCommentsReviewDataAccessInterface;
import use_case.get_user_reviews.GetUserReviewsDataAccessInterface;
import use_case.like_review.LikeReviewDataAccessInterface;
import use_case.recommendation.ReviewedMediaRatingDataAccessInterface;
import use_case.recommendation.UserRating;
import use_case.unlike_review.UnlikeReviewDataAccessInterface;

/**
 * MongoDB data access object for review data.
 */
public class MongoReviewDataAccessObject implements
        CreateReviewDataAccessInterface,
        DeleteReviewDataAccessInterface,
        EditReviewDataAccessInterface,
        GetMediaReviewsDataAccessInterface,
        GetUserCommentsReviewDataAccessInterface,
        GetUserReviewsDataAccessInterface,
        LikeReviewDataAccessInterface,
        ReviewedMediaRatingDataAccessInterface,
        UnlikeReviewDataAccessInterface {

    private static final String DEFAULT_PROPERTIES = "mongo.properties";
    private static final String DEFAULT_COLLECTION = "reviews";
    private static final String DEFAULT_LIKES_COLLECTION = "reviewLikes";
    private static final String MOVIE_TYPE = "movie";
    private static final String TV_TYPE = "tv";

    private static final String REVIEW_ID = "reviewId";
    private static final String MEDIA_ID = "mediaId";
    private static final String MEDIA_TYPE = "mediaType";
    private static final String MEDIA_TITLE = "mediaTitle";
    private static final String RELEASE_YEAR = "releaseYear";
    private static final String POSTER_PATH = "posterPath";
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
    private final MongoCollection<Document> reviewLikes;
    private final TmdbApiClient tmdbApiClient = new TmdbApiClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Connects using the default properties file.
     */
    public MongoReviewDataAccessObject() {
        this(DEFAULT_PROPERTIES);
    }

    /**
     * Connects using the given properties file.
     * @param propertiesPath the path to the MongoDB properties file
     */
    public MongoReviewDataAccessObject(String propertiesPath) {
        final Properties properties = loadProperties(propertiesPath);
        mongoClient = MongoClients.create(properties.getProperty("uri"));

        final MongoDatabase database = mongoClient.getDatabase(
                properties.getProperty("database"));
        reviews = database.getCollection(properties.getProperty(
                "reviewsCollection", DEFAULT_COLLECTION));
        reviewLikes = database.getCollection(properties.getProperty(
                "reviewLikesCollection", DEFAULT_LIKES_COLLECTION));
    }

    /**
     * Saves a review.
     * @param review the review to save
     */
    public void saveReview(Review review) {
        reviews.replaceOne(Filters.eq(REVIEW_ID, review.getReviewId()),
                toDocument(review), new ReplaceOptions().upsert(true));
    }

    /**
     * Returns whether a review exists.
     * @param reviewId the review id to check
     * @return true if a review with this id exists
     */
    public boolean existsByReviewId(String reviewId) {
        return reviews.find(Filters.eq(REVIEW_ID, reviewId)).first() != null;
    }

    /**
     * Returns a review by id.
     * @param reviewId the review id to search for
     * @return the review, if it exists
     */
    public Optional<Review> getReviewById(String reviewId) {
        final Document document = reviews.find(Filters.eq(REVIEW_ID, reviewId)).first();
        return Optional.ofNullable(toReview(document));
    }

    /**
     * Returns all reviews written by a user.
     * @param username the author's username
     * @return the matching reviews
     */
    public List<Review> getReviewsByUsername(String username) {
        final List<Review> matchingReviews = new ArrayList<>();

        for (Document document : reviews.find(Filters.eq(AUTHOR_USERNAME, username))) {
            matchingReviews.add(toReview(document));
        }

        return matchingReviews;
    }

    /**
     * Returns all reviews for one media item.
     * @param mediaId the media id
     * @param mediaType the media type
     * @return the matching reviews
     */
    public List<Review> getReviewsByMedia(int mediaId, String mediaType) {
        final List<Review> matchingReviews = new ArrayList<>();

        for (Document document : reviews.find(Filters.and(
                Filters.eq(MEDIA_ID, mediaId),
                Filters.eq(MEDIA_TYPE, mediaType)))) {
            matchingReviews.add(toReview(document));
        }

        return matchingReviews;
    }

    /**
     * Updates an existing review.
     * @param reviewId the review id
     * @param newRating the updated rating
     * @param newReviewText the updated review text
     * @param newUpdatedAt the updated timestamp
     * @return true if the review was updated
     */
    public boolean editReview(String reviewId, double newRating, String newReviewText,
                              ZonedDateTime newUpdatedAt) {
        reviews.updateOne(Filters.eq(REVIEW_ID, reviewId),
                Updates.combine(
                        Updates.set(RATING, newRating),
                        Updates.set(REVIEW_TEXT, newReviewText),
                        Updates.set(UPDATED_AT, newUpdatedAt.toString())));
        return existsByReviewId(reviewId);
    }

    /**
     * Deletes a review.
     * @param reviewId the review id
     * @return true if the review was deleted
     */
    public boolean deleteReview(String reviewId) {
        return reviews.deleteOne(Filters.eq(REVIEW_ID, reviewId))
                .getDeletedCount() > 0;
    }

    /**
     * Adds a user's like to a review.
     * @param reviewId the review id
     * @param username the username liking the review
     * @return true if the review exists
     */
    public boolean likeReview(String reviewId, String username) {
        final boolean localReviewExists = existsByReviewId(reviewId);
        if (localReviewExists) {
            reviews.updateOne(Filters.eq(REVIEW_ID, reviewId),
                    Updates.addToSet(LIKED_BY_USERNAMES, username));
        } else {
            reviewLikes.updateOne(Filters.eq(REVIEW_ID, reviewId),
                    Updates.addToSet(LIKED_BY_USERNAMES, username),
                    new com.mongodb.client.model.UpdateOptions().upsert(true));
        }
        return true;
    }

    /**
     * Removes a user's like from a review.
     * @param reviewId the review id
     * @param username the username unliking the review
     * @return true if the review exists
     */
    public boolean unlikeReview(String reviewId, String username) {
        final boolean localReviewExists = existsByReviewId(reviewId);
        if (localReviewExists) {
            reviews.updateOne(Filters.eq(REVIEW_ID, reviewId),
                    Updates.pull(LIKED_BY_USERNAMES, username));
        } else {
            reviewLikes.updateOne(Filters.eq(REVIEW_ID, reviewId),
                    Updates.pull(LIKED_BY_USERNAMES, username));
        }
        return true;
    }

    /**
     * Returns locally stored likes for an external review id.
     * @param reviewId the external review id
     * @return usernames that liked the review
     */
    public Set<String> getLikedByUsernames(String reviewId) {
        final Document document = reviewLikes.find(Filters.eq(REVIEW_ID,
                reviewId)).first();
        final Set<String> likedByUsernames;
        if (document == null) {
            likedByUsernames = new HashSet<>();
        } else {
            likedByUsernames = new HashSet<>(document.getList(
                    LIKED_BY_USERNAMES, String.class, new ArrayList<>()));
        }
        return likedByUsernames;
    }

    /**
     * Returns all saved reviews.
     * @return all reviews
     */
    public List<Review> getAllReviews() {
        final List<Review> allReviews = new ArrayList<>();

        for (Document document : reviews.find()) {
            allReviews.add(toReview(document));
        }

        return allReviews;
    }

    /**
     * Closes the MongoDB connection.
     */
    public void close() {
        mongoClient.close();
    }

    @Override
    public List<UserRating> findReviewRatingsByUser(final String username) {
        final List<UserRating> userRatings = new ArrayList<>();
        for (Review review : getReviewsByUsername(username)) {
            userRatings.add(new UserRating(review.getMediaId(),
                    review.getRating()));
        }
        return userRatings;
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
                .append(RELEASE_YEAR, review.getReleaseYear())
                .append(POSTER_PATH, review.getPosterPath())
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
            final MediaMetadata mediaMetadata = getMediaMetadata(document);
            final List<String> likedByUsernames = document.getList(
                    LIKED_BY_USERNAMES, String.class, new ArrayList<>());
            review = new Review(document.getString(REVIEW_ID),
                    document.getInteger(MEDIA_ID),
                    document.getString(MEDIA_TYPE),
                    document.getString(MEDIA_TITLE),
                    mediaMetadata.getReleaseYear(),
                    mediaMetadata.getPosterPath(),
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

    private MediaMetadata getMediaMetadata(final Document document) {
        MediaMetadata mediaMetadata = new MediaMetadata(
                document.getInteger(RELEASE_YEAR, 0),
                document.getString(POSTER_PATH));
        if (mediaMetadata.isMissingPosterOrYear()) {
            mediaMetadata = loadMediaMetadata(
                    document.getInteger(MEDIA_ID),
                    document.getString(MEDIA_TYPE),
                    mediaMetadata);
            cacheMediaMetadata(document.getString(REVIEW_ID), mediaMetadata);
        }
        return mediaMetadata;
    }

    private MediaMetadata loadMediaMetadata(final int mediaId,
                                            final String mediaType,
                                            final MediaMetadata fallback) {
        MediaMetadata mediaMetadata = fallback;
        try {
            final String detailsJson;
            if (MOVIE_TYPE.equals(mediaType)) {
                detailsJson = tmdbApiClient.getMovieDetails(mediaId);
                mediaMetadata = parseMovieMetadata(detailsJson);
            } else if (TV_TYPE.equals(mediaType)) {
                detailsJson = tmdbApiClient.getTvShowDetails(mediaId);
                mediaMetadata = parseTvMetadata(detailsJson);
            }
        } catch (IOException | IllegalStateException exception) {
            mediaMetadata = fallback;
        }
        return mediaMetadata;
    }

    private MediaMetadata parseMovieMetadata(final String detailsJson)
            throws IOException {
        final JsonNode details = objectMapper.readTree(detailsJson);
        return new MediaMetadata(parseYear(details.path("release_date")
                .asText()), details.path("poster_path").asText(""));
    }

    private MediaMetadata parseTvMetadata(final String detailsJson)
            throws IOException {
        final JsonNode details = objectMapper.readTree(detailsJson);
        return new MediaMetadata(parseYear(details.path("first_air_date")
                .asText()), details.path("poster_path").asText(""));
    }

    private int parseYear(final String date) {
        int year = 0;
        if (date != null && date.length() >= 4) {
            try {
                year = Integer.parseInt(date.substring(0, 4));
            } catch (NumberFormatException exception) {
                year = 0;
            }
        }
        return year;
    }

    private void cacheMediaMetadata(final String reviewId,
                                    final MediaMetadata mediaMetadata) {
        if (reviewId != null && !mediaMetadata.isMissingPosterOrYear()) {
            reviews.updateOne(Filters.eq(REVIEW_ID, reviewId),
                    Updates.combine(
                            Updates.set(RELEASE_YEAR,
                                    mediaMetadata.getReleaseYear()),
                            Updates.set(POSTER_PATH,
                                    mediaMetadata.getPosterPath())));
        }
    }

    /**
     * Media display metadata for a review.
     */
    private static final class MediaMetadata {
        /**
         * The release year.
         */
        private final int releaseYear;
        /**
         * The poster path.
         */
        private final String posterPath;

        private MediaMetadata(final int inputReleaseYear,
                              final String inputPosterPath) {
            this.releaseYear = inputReleaseYear;
            this.posterPath = inputPosterPath;
        }

        private int getReleaseYear() {
            return releaseYear;
        }

        private String getPosterPath() {
            return posterPath;
        }

        private boolean isMissingPosterOrYear() {
            return releaseYear == 0 || posterPath == null
                    || posterPath.isEmpty();
        }
    }
}
