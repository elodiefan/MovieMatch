package use_case.review.unlike_review;

/**
 * Output boundary for unliking a review.
 */
public interface UnlikeReviewOutputBoundary {
    /**
     * Prepares the success view after unliking a review.
     * @param unliked whether the review was unliked
     */
    void prepareSuccessView(boolean unliked);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
