package use_case.review;

import java.time.ZonedDateTime;

/**
 * Display-safe review data shared by review use cases.
 */
public final class ReviewSummaryData {
    private final String reviewId;
    private final int mediaId;
    private final String mediaType;
    private final String mediaTitle;
    private final String authorUsername;
    private final String authorDisplayName;
    private final double rating;
    private final String reviewText;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final int likeCount;
    private final String source;

    /**
     * Creates display-safe review data.
     */
    public ReviewSummaryData(final String reviewId, final int mediaId,
                             final String mediaType, final String mediaTitle,
                             final String authorUsername,
                             final String authorDisplayName,
                             final double rating, final String reviewText,
                             final ZonedDateTime createdAt,
                             final ZonedDateTime updatedAt,
                             final int likeCount, final String source) {
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
        this.likeCount = likeCount;
        this.source = source;
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
