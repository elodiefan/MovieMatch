package use_case.review.unlike_review;

/**
 * Output boundary for unliking a review.
 */
public interface UnlikeReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     * @param outputData the outputData
     */
    void prepareSuccessView(UnlikeReviewOutputData outputData);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
