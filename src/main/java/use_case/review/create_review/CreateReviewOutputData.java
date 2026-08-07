package use_case.review.create_review;

import entity.Review;
import use_case.review.ReviewSummaryData;
import use_case.review.ReviewSummaryMapper;

/**
 * Output data for creating a review.
 */
public final class CreateReviewOutputData {
    /** The review. */
    private final ReviewSummaryData review;

    /**
     * Handles this review or comment operation.
     */
    public CreateReviewOutputData(final Review inputReview) {
        this.review = ReviewSummaryMapper.toSummary(inputReview);
    }

    /**
     * Handles this review or comment operation.
     */
    public ReviewSummaryData getReview() {
        return review;
    }
}
