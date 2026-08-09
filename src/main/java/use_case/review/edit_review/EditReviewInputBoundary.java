package use_case.review.edit_review;

/**
 * Input boundary for editing a review.
 */
public interface EditReviewInputBoundary {
    /**
     * Executes the use case.
     * @param reviewId the review id
     * @param username the username requesting the edit
     * @param rating the updated rating
     * @param reviewText the updated review text
     */
    void execute(String reviewId, String username, double rating,
                 String reviewText);
}
