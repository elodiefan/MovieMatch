package use_case.review;

/**
 * Output boundary for creating a review.
 */
public interface CreateReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     * @param outputData the outputData
     */
    void prepareSuccessView(CreateReviewOutputData outputData);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
