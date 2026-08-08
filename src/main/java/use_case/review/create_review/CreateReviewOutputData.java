package use_case.review.create_review;

import entity.Review;

/**
 * Output data for creating a review.
 */
public final class CreateReviewOutputData {
    /**
     * The review.
     */
    private final Review review;

    /**
     * Handles this review or comment operation.
     * @param inputReview the inputReview
     */
    public CreateReviewOutputData(final Review inputReview) {
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
