package use_case.review;

import java.util.ArrayList;
import java.util.List;

import entity.Review;

/**
 * Output data for loading reviews written by one user.
 */
public class GetUserReviewsOutputData {
    private final List<Review> reviews;

    /**
     * Creates output data for loaded user reviews.
     * @param reviews the loaded reviews
     */
    public GetUserReviewsOutputData(final List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }

    /**
     * Returns loaded user reviews.
     * @return a copy of the reviews
     */
    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }
}
