package use_case.delete_review;

/**
 * Output boundary for deleting a review.
 */
public interface DeleteReviewOutputBoundary {
    /**
     * Prepares the success view after deleting a review.
     * @param deleted whether the review was deleted
     */
    void prepareSuccessView(boolean deleted);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
