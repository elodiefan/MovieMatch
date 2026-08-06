package use_case.review;

/**
 * Output boundary for editing a review.
 */
public interface EditReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     * @param outputData the outputData
     */
    void prepareSuccessView(EditReviewOutputData outputData);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
