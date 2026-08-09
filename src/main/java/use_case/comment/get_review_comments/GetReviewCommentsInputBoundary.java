package use_case.comment.get_review_comments;

/**
 * Input boundary for loading comments on a review.
 */
public interface GetReviewCommentsInputBoundary {
    /**
     * Executes the use case.
     * @param reviewId the review id
     */
    void execute(String reviewId);
}
