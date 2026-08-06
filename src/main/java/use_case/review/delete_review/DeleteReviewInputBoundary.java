package use_case.review.delete_review;

/**
 * Input boundary for deleting a review.
 */
public interface DeleteReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(DeleteReviewInputData inputData);
}
