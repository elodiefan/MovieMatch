package use_case.comment;

/**
 * Input data for loading comments on a review.
 */
public class GetReviewCommentsInputData {
    private final String reviewId;

    public GetReviewCommentsInputData(final String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewId() { return reviewId; }
}
