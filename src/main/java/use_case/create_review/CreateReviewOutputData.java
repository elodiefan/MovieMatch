package use_case.create_review;

import use_case.get_media_reviews.ReviewSummaryData;

/**
 * Output data for creating a review.
 */
public final class CreateReviewOutputData {
    /** The review. */
    private final ReviewSummaryData review;

    /**
     * Handles this review or comment operation.
     */
    public CreateReviewOutputData(final ReviewSummaryData inputReview) {
        this.review = inputReview;
    }

    /**
     * Handles this review or comment operation.
     */
    public ReviewSummaryData getReview() {
        return review;
    }
}
