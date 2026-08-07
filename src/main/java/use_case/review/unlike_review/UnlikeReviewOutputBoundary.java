package use_case.review.unlike_review;

/**
 * Output boundary for unliking a review.
 */
public interface UnlikeReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(boolean unliked);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
