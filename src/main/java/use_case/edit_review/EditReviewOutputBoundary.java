package use_case.edit_review;

/**
 * Output boundary for editing a review.
 */
public interface EditReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(EditReviewOutputData review);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
