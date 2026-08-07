package use_case.edit_review;

import use_case.get_media_reviews.ReviewSummaryData;

/**
 * Output data for editing a review.
 */
public final class EditReviewOutputData {
    /** The review. */
    private final ReviewSummaryData review;

    /**
     * Handles this review or comment operation.
     */
    public EditReviewOutputData(final ReviewSummaryData inputReview) {
        this.review = inputReview;
    }

    /**
     * Handles this review or comment operation.
     */
    public ReviewSummaryData getReview() {
        return review;
    }
}
