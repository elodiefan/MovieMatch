package use_case.review.edit_review;

import entity.Review;
import use_case.review.ReviewSummaryData;
import use_case.review.ReviewSummaryMapper;

/**
 * Output data for editing a review.
 */
public final class EditReviewOutputData {
    /** The review. */
    private final ReviewSummaryData review;

    /**
     * Handles this review or comment operation.
     */
    public EditReviewOutputData(final Review inputReview) {
        this.review = ReviewSummaryMapper.toSummary(inputReview);
    }

    /**
     * Handles this review or comment operation.
     */
    public ReviewSummaryData getReview() {
        return review;
    }
}
