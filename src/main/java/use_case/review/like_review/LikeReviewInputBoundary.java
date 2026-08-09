package use_case.review.like_review;

/**
 * Input boundary for liking a review.
 */
public interface LikeReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data for liking a review
     */
    void execute(LikeReviewInputData inputData);
}
