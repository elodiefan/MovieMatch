package entity;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Represents a user's review on a piece of media.
 */
public class Review extends UserContent {
    private final int mediaId;
    private final String mediaType;
    private final String mediaTitle;
    private final String source;
    private double rating;
    private String reviewText;
    private ZonedDateTime updatedAt;

    /**
     * Creates a review.
     */
    public Review(String reviewId, int mediaId, String mediaType, String mediaTitle,
                  String authorUsername, String authorDisplayName, double rating,
                  String reviewText, ZonedDateTime createdAt, ZonedDateTime updatedAt,
                  String source, Set<String> likedByUsernames) {
        super(reviewId, authorUsername, authorDisplayName, createdAt, likedByUsernames);
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.mediaTitle = mediaTitle;
        this.rating = rating;
        this.reviewText = reviewText;
        this.updatedAt = updatedAt;
        this.source = source;
    }

    /**
     * Returns the unique identifier for this review.
     */
    public String getReviewId() {
        return getContentId();
    }

    /**
     * Returns the reviewed media's identifier.
     */
    public int getMediaId() {
        return mediaId;
    }

    /**
     * Returns the reviewed media's type.
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Returns the reviewed media's title.
     */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /**
     * Returns the rating left by the author.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Returns the body of the review.
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Returns the time the review was last updated.
     */
    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Returns the source of the review.
     */
    public String getSource() {
        return source;
    }

    /**
     * Updates the editable review fields.
     */
    public void edit(double newRating, String newReviewText, ZonedDateTime newUpdatedAt) {
        this.rating = newRating;
        this.reviewText = newReviewText;
        this.updatedAt = newUpdatedAt;
    }

}
