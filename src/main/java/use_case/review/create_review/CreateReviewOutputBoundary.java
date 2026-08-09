package use_case.review.create_review;

/**
 * Output boundary for creating a review.
 */
public interface CreateReviewOutputBoundary {
    /**
     * Prepares the success view after creating a review.
     * @param review the created review output data
     */
    void prepareSuccessView(CreateReviewOutputData review);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
