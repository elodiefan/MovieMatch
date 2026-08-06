package use_case.review.unlike_review;

/**
 * Input data for unliking a review.
 */
public final class UnlikeReviewInputData {
    /** The review id. */
    private final String reviewId;
    /** The username. */
    private final String username;

    /**
     * Handles this review or comment operation.
     * @param inputReviewId the inputReviewId
     * @param inputUsername the inputUsername
     */
    public UnlikeReviewInputData(final String inputReviewId,
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
