package use_case.review.edit_review;

/**
 * Input boundary for editing a review.
 */
public interface EditReviewInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String reviewId, String username, double rating,
                 String reviewText);
}
