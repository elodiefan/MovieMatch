package use_case.review;

/**
 * Input data for liking a review.
 */
public class LikeReviewInputData {
    private final String reviewId;
    private final String username;

    public LikeReviewInputData(final String reviewId, final String username) {
        this.reviewId = reviewId;
        this.username = username;
    }

    public String getReviewId() { return reviewId; }
    public String getUsername() { return username; }
}
