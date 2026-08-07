package use_case.review.delete_review;

/** Input data for deleting a review. */
public final class DeleteReviewInputData {
    /** The review id. */
    private final String reviewId;
    /** The username. */
    private final String username;

    /** Handles this review or comment operation. */
    public DeleteReviewInputData(final String inputReviewId,
                                 final String inputUsername) {
        this.reviewId = inputReviewId;
        this.username = inputUsername;
    }

    /** Handles this review or comment operation. */
    public String getReviewId() {
        return reviewId;
    }
    /** Handles this review or comment operation. */
    public String getUsername() {
        return username;
    }
}
