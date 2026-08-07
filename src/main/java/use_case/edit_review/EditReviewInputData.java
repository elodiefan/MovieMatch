package use_case.edit_review;

/**
 * Input data for editing a review.
 */
public final class EditReviewInputData {
    /** The review id. */
    private final String reviewId;
    /** The username. */
    private final String username;
    /** The rating. */
    private final double rating;
    /** The review text. */
    private final String reviewText;

    /**
     * Handles this review or comment operation.
     */
    public EditReviewInputData(final String inputReviewId,
                               final String inputUsername,
                               final double inputRating,
                               final String inputReviewText) {
        this.reviewId = inputReviewId;
        this.username = inputUsername;
        this.rating = inputRating;
        this.reviewText = inputReviewText;
    }

    /**
     * Handles this review or comment operation.
     */
    public String getReviewId() {
        return reviewId;
    }
    /**
     * Handles this review or comment operation.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Handles this review or comment operation.
     */
    public double getRating() {
        return rating;
    }
    /**
     * Handles this review or comment operation.
     */
    public String getReviewText() {
        return reviewText;
    }
}
