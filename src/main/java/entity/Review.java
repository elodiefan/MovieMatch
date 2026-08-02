package entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user's review on a piece of media.
 */
public class Review extends UserContent {
    private static final ZoneId TORONTO_ZONE = ZoneId.of("America/Toronto");

    private final String reviewId;
    private final int mediaId;
    private final String mediaType;
    private final String mediaTitle;
    private final String authorUsername;
    private final String authorDisplayName;
    private final ZonedDateTime createdAt;
    private final String source;
    private final Set<String> likedByUsernames;
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
        this.reviewId = reviewId;
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.mediaTitle = mediaTitle;
        this.authorUsername = authorUsername;
        this.authorDisplayName = authorDisplayName;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.source = source;
        this.likedByUsernames = new HashSet<>(likedByUsernames);
    }

    /**
     * Returns the current date and time in Toronto.
     * @return the current Toronto date and time
     */
    public static ZonedDateTime getCurrentTorontoTime() {
        return ZonedDateTime.now(TORONTO_ZONE);
    }

    /**
     * Returns the unique identifier for this review.
     * @return the review id
     */
    public String getReviewId() {
        return reviewId;
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
     * Returns the username of the review author.
     * @return the author's username
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Returns the display name of the review author.
     * @return the author's display name
     */
    public String getAuthorDisplayName() {
        return authorDisplayName;
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
     * Returns the time the review was created.
     * @return the creation time
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
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
     * Returns the usernames of users who liked this review.
     * @return a copy of the liked-by usernames
     */
    public Set<String> getLikedByUsernames() {
        return new HashSet<>(likedByUsernames);
    }

    /**
     * Returns the number of likes on this review.
     * @return the like count
     */
    public int getLikeCount() {
        return likedByUsernames.size();
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

    /**
     * Adds a like from a user.
     * @param username the username liking the review
     */
    public void like(String username) {
        likedByUsernames.add(username);
    }

    /**
     * Removes a like from a user.
     * @param username the username unliking the review
     */
    public void unlike(String username) {
        likedByUsernames.remove(username);
    }
}
