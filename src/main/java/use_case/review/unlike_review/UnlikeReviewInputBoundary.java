package use_case.review.unlike_review;

/**
 * Input boundary for unliking a review.
 */
public interface UnlikeReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data for unliking a review
     */
    void execute(UnlikeReviewInputData inputData);
}
