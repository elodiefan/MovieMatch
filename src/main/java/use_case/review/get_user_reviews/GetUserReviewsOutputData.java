package use_case.review.get_user_reviews;

import java.util.ArrayList;
import java.util.List;

import entity.Review;

/**
 * Output data for loading reviews written by one user.
 */
public final class GetUserReviewsOutputData {
    /** The reviews. */
    private final List<Review> reviews;

    /**
     * Creates output data for loaded user reviews.
     */
    public GetUserReviewsOutputData(final List<Review> inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /**
     * Returns loaded user reviews.
     */
    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }
}
