package use_case.review.like_review;

/**
 * Output boundary for liking a review.
 */
public interface LikeReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(LikeReviewOutputData outputData);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
