package use_case.review;

/**
 * Input data for deleting a review.
 */
public class DeleteReviewInputData {
    private final String reviewId;
    private final String username;

    public DeleteReviewInputData(final String reviewId, final String username) {
        this.reviewId = reviewId;
        this.username = username;
    }

    public String getReviewId() { return reviewId; }
    public String getUsername() { return username; }
}
