package use_case.review.create_review;

import entity.Review;

/** Output data for creating a review. */
public final class CreateReviewOutputData {
    /** The review. */
    private final Review review;

    /** Handles this review or comment operation. */
    public CreateReviewOutputData(final Review inputReview) {
        this.review = inputReview;
    }

    /** Handles this review or comment operation. */
    public Review getReview() {
        return review;
    }
}
