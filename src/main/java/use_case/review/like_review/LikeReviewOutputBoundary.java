package use_case.review.like_review;

/**
 * Output boundary for liking a review.
 */
public interface LikeReviewOutputBoundary {
    /**
     * Prepares the success view after liking a review.
     * @param liked whether the review was liked
     */
    void prepareSuccessView(boolean liked);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
