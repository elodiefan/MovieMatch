package use_case.review.unlike_review;

/**
 * Input boundary for unliking a review.
 */
public interface UnlikeReviewInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(UnlikeReviewInputData inputData);
}
