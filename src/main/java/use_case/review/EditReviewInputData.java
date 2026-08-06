package use_case.review;

/**
 * Input data for editing a review.
 */
public class EditReviewInputData {
    private final String reviewId;
    private final String username;
    private final double rating;
    private final String reviewText;

    public EditReviewInputData(final String reviewId, final String username,
                               final double rating, final String reviewText) {
        this.reviewId = reviewId;
        this.username = username;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public String getReviewId() { return reviewId; }
    public String getUsername() { return username; }
    public double getRating() { return rating; }
    public String getReviewText() { return reviewText; }
}
