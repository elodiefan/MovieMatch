package use_case.review.unlike_review;

/**
 * Input boundary for unliking a review.
 */
public interface UnlikeReviewInputBoundary {
    /**
     * Executes the use case.
     * @param reviewId the review id
     * @param username the username unliking the review
     */
    void execute(String reviewId, String username);
}
