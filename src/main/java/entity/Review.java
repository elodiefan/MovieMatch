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
     * @param reviewId the unique identifier for this review
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @param mediaTitle the reviewed media's title
     * @param authorUsername the username of the review author
     * @param authorDisplayName the display name of the review author
     * @param rating the rating left by the author
     * @param reviewText the body of the review
     * @param createdAt the time the review was created
     * @param updatedAt the time the review was last updated
     * @param source the source of the review
     * @param likedByUsernames the usernames of users who liked this review
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
     * @return the review id
     */
    public String getReviewId() {
        return getContentId();
    }

    /**
     * Returns the reviewed media's identifier.
     * @return the media id
     */
    public int getMediaId() {
        return mediaId;
    }

    /**
     * Returns the reviewed media's type.
     * @return the media type
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Returns the reviewed media's title.
     * @return the media title
     */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /**
     * Returns the rating left by the author.
     * @return the review rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Returns the body of the review.
     * @return the review text
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Returns the time the review was last updated.
     * @return the last update time
     */
    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Returns the source of the review.
     * @return the review source
     */
    public String getSource() {
        return source;
    }

    /**
     * Updates the editable review fields.
     * @param newRating the updated rating
     * @param newReviewText the updated review text
     * @param newUpdatedAt the updated timestamp
     */
    public void edit(double newRating, String newReviewText, ZonedDateTime newUpdatedAt) {
        this.rating = newRating;
        this.reviewText = newReviewText;
        this.updatedAt = newUpdatedAt;
    }

}
