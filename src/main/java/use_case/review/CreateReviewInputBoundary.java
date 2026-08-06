package use_case.review;

/**
 * Input boundary for creating a review.
 */
public interface CreateReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(CreateReviewInputData inputData);
}
