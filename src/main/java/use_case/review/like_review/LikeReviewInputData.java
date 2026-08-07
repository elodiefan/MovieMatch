package use_case.review.like_review;

/** Input data for liking a review. */
public final class LikeReviewInputData {
    /** The review id. */
    private final String reviewId;
    /** The username. */
    private final String username;

    /** Handles this review or comment operation. */
    public LikeReviewInputData(final String inputReviewId,
                               final String inputUsername) {
        this.reviewId = inputReviewId;
        this.username = inputUsername;
    }

    /** Handles this review or comment operation. */
    public String getReviewId() {
        return reviewId;
    }
    /** Handles this review or comment operation. */
    public String getUsername() {
        return username;
    }
}
