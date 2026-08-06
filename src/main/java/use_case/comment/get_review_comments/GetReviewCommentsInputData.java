package use_case.comment.get_review_comments;

/**
 * Input data for loading comments on a review.
 */
public final class GetReviewCommentsInputData {
    /** The review id. */
    private final String reviewId;

    /**
     * Handles this review or comment operation.
     * @param inputReviewId the inputReviewId
     */
    public GetReviewCommentsInputData(final String inputReviewId) {
        this.reviewId = inputReviewId;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getReviewId() {
        return reviewId;
    }
}
