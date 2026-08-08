package use_case.review.delete_review;

/**
 * Input boundary for deleting a review.
 */
public interface DeleteReviewInputBoundary {
    /**
     * Executes the use case.
     * @param reviewId the review id
     * @param username the username requesting deletion
     */
    void execute(String reviewId, String username);
}
