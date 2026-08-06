package use_case.review;

/**
 * Input data for unliking a review.
 */
public class UnlikeReviewInputData {
    private final String reviewId;
    private final String username;

    public UnlikeReviewInputData(final String reviewId, final String username) {
        this.reviewId = reviewId;
        this.username = username;
    }

    public String getReviewId() { return reviewId; }
    public String getUsername() { return username; }
}
