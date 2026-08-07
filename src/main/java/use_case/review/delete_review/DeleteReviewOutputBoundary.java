package use_case.review.delete_review;

/**
 * Output boundary for deleting a review.
 */
public interface DeleteReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(DeleteReviewOutputData outputData);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
