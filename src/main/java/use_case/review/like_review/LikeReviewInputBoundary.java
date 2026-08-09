package use_case.review.like_review;

/**
 * Input boundary for liking a review.
 */
public interface LikeReviewInputBoundary {
    /**
     * Executes the use case.
     * @param reviewId the review id
     * @param username the username liking the review
     */
    void execute(String reviewId, String username);
}
