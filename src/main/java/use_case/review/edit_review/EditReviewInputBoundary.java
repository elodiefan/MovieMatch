package use_case.review.edit_review;

/**
 * Input boundary for editing a review.
 */
public interface EditReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data for editing a review
     */
    void execute(EditReviewInputData inputData);
}
