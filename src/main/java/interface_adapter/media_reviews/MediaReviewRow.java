package interface_adapter.media_reviews;

import java.time.ZonedDateTime;

/** Display data for one community review on a media page. */
public final class MediaReviewRow {
    /** The review id. */
    private final String reviewId;
    /** The author username. */
    private final String authorUsername;
    /** The author display name. */
    private final String authorDisplayName;
    /** The rating. */
    private final double rating;
    /** The review text. */
    private final String reviewText;
    /** The created at. */
    private final ZonedDateTime createdAt;
    /** The updated at. */
    private final ZonedDateTime updatedAt;
    /** The like count. */
    private final int likeCount;
    /** The review source. */
    private final String source;

    /** Creates display data for one media review row. */
    public MediaReviewRow(final String inputReviewId,
                          final String inputAuthorUsername,
                          final String inputAuthorDisplayName,
                          final double inputRating,
                          final String inputReviewText,
                          final ZonedDateTime inputCreatedAt,
                          final ZonedDateTime inputUpdatedAt,
                          final int inputLikeCount,
                          final String inputSource) {
        this.reviewId = inputReviewId;
        this.authorUsername = inputAuthorUsername;
        this.authorDisplayName = inputAuthorDisplayName;
        this.rating = inputRating;
        this.reviewText = inputReviewText;
        this.createdAt = inputCreatedAt;
        this.updatedAt = inputUpdatedAt;
        this.likeCount = inputLikeCount;
        this.source = inputSource;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
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

    public String getSource() {
        return source;
    }
}
