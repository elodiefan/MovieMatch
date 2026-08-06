package use_case.review;

/**
 * Input boundary for unliking a review.
 */
public interface UnlikeReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(UnlikeReviewInputData inputData);
}
