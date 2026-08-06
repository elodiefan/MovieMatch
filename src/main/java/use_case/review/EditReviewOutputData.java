package use_case.review;

import entity.Review;

/**
 * Output data for editing a review.
 */
public class EditReviewOutputData {
    private final Review review;

    public EditReviewOutputData(final Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }
}
