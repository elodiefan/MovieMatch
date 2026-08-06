package use_case.review;

/**
 * Input boundary for liking a review.
 */
public interface LikeReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(LikeReviewInputData inputData);
}
