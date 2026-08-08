package interface_adapter.user_reviews;

import java.time.ZonedDateTime;

/**
 * Display data for one review written by the user.
 */
public final class UserReviewRow {
    /**
     * The review id.
     */
    private final String reviewId;
    /**
     * The media id.
     */
    private final int mediaId;
    /**
     * The media type.
     */
    private final String mediaType;
    /**
     * The media title.
     */
    private final String mediaTitle;
    /**
     * The release year.
     */
    private final int releaseYear;
    /**
     * The poster path.
     */
    private final String posterPath;
    /**
     * The rating.
     */
    private final double rating;
    /**
     * The review text.
     */
    private final String reviewText;
    /**
     * The created at.
     */
    private final ZonedDateTime createdAt;
    /**
     * The updated at.
     */
    private final ZonedDateTime updatedAt;
    /**
     * The like count.
     */
    private final int likeCount;

    /**
     * Creates display data for one user review row.
     * @param inputReviewId the review id
     * @param inputMediaId the media id
     * @param inputMediaType the media type
     * @param inputMediaTitle the media title
     * @param inputReleaseYear the release year
     * @param inputPosterPath the poster path
     * @param inputRating the rating
     * @param inputReviewText the review text
     * @param inputCreatedAt the creation time
     * @param inputUpdatedAt the update time
     * @param inputLikeCount the like count
     */
    public UserReviewRow(final String inputReviewId,
                         final int inputMediaId,
                         final String inputMediaType,
                         final String inputMediaTitle,
                         final int inputReleaseYear,
                         final String inputPosterPath,
                         final double inputRating,
                         final String inputReviewText,
                         final ZonedDateTime inputCreatedAt,
                         final ZonedDateTime inputUpdatedAt,
                         final int inputLikeCount) {
        this.reviewId = inputReviewId;
        this.mediaId = inputMediaId;
        this.mediaType = inputMediaType;
        this.mediaTitle = inputMediaTitle;
        this.releaseYear = inputReleaseYear;
        this.posterPath = inputPosterPath;
        this.rating = inputRating;
        this.reviewText = inputReviewText;
        this.createdAt = inputCreatedAt;
        this.updatedAt = inputUpdatedAt;
        this.likeCount = inputLikeCount;
    }

    public String getReviewId() {
        return reviewId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
