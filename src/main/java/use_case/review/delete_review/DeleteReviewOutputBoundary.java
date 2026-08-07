package use_case.review.delete_review;

/**
 * Output boundary for deleting a review.
 */
public interface DeleteReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(boolean deleted);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
