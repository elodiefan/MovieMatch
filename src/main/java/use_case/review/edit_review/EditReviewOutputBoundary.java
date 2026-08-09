package use_case.review.edit_review;

/**
 * Output boundary for editing a review.
 */
public interface EditReviewOutputBoundary {
    /**
     * Prepares the success view after editing a review.
     * @param review the edited review output data
     */
    void prepareSuccessView(EditReviewOutputData review);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
