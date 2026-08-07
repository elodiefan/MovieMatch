package use_case.review.like_review;

/**
 * Input boundary for liking a review.
 */
public interface LikeReviewInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String reviewId, String username);
}
