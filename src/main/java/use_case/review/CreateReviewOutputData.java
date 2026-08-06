package use_case.review;

import entity.Review;

/**
 * Output data for creating a review.
 */
public class CreateReviewOutputData {
    private final Review review;

    public CreateReviewOutputData(final Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }
}
