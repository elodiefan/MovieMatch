package use_case.review;

import entity.Review;

/**
 * Output data for editing a review.
 */
public final class EditReviewOutputData {
    /** The review. */
    private final Review review;

    /**
     * Handles this review or comment operation.
     * @param inputReview the inputReview
     */
    public EditReviewOutputData(final Review inputReview) {
        this.review = inputReview;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public Review getReview() {
        return review;
    }
}
