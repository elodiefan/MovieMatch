package use_case.review.create_review;

import use_case.review.ReviewSummaryData;

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
