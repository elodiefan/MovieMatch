package use_case.like_review;

/**
 * Output boundary for liking a review.
 */
public interface LikeReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(boolean liked);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
