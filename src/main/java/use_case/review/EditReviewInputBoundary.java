package use_case.review;

/**
 * Input boundary for editing a review.
 */
public interface EditReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(EditReviewInputData inputData);
}
