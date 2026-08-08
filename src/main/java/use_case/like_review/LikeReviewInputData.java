package use_case.like_review;

/**
 * Input data for liking a review.
 */
public final class LikeReviewInputData {
    /**
     * The review id.
     */
    private final String reviewId;
    /**
     * The username.
     */
    private final String username;

    /**
     * Handles this review or comment operation.
     * @param inputReviewId the inputReviewId
     * @param inputUsername the inputUsername
     */
    public LikeReviewInputData(final String inputReviewId,
                               final String inputUsername) {
        this.reviewId = inputReviewId;
        this.username = inputUsername;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getReviewId() {
        return reviewId;
    }
    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getUsername() {
        return username;
    }
}
