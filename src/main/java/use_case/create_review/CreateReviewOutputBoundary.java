package use_case.create_review;

/**
 * Output boundary for creating a review.
 */
public interface CreateReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(CreateReviewOutputData review);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
