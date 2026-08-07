package use_case.create_review;

import java.time.ZonedDateTime;

/**
 * Output data for creating a review.
 */
public final class CreateReviewOutputData {
    private final String reviewId;
    private final String authorUsername;
    private final String authorDisplayName;
    private final double rating;
    private final String reviewText;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final int likeCount;
    private final String source;

    /**
     * Creates output data for a created review.
     */
    public CreateReviewOutputData(final String reviewId,
                                  final String authorUsername,
                                  final String authorDisplayName,
                                  final double rating,
                                  final String reviewText,
                                  final ZonedDateTime createdAt,
                                  final ZonedDateTime updatedAt,
                                  final int likeCount,
                                  final String source) {
        this.reviewId = reviewId;
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
