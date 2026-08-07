package use_case.get_review_comments;

/**
 * Input boundary for loading comments on a review.
 */
public interface GetReviewCommentsInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String reviewId);
}
