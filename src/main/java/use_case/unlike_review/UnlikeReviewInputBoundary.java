package use_case.unlike_review;

/**
 * Input boundary for unliking a review.
 */
public interface UnlikeReviewInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String reviewId, String username);
}
