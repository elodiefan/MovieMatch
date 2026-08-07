package use_case.review.delete_review;

/**
 * Input boundary for deleting a review.
 */
public interface DeleteReviewInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String reviewId, String username);
}
