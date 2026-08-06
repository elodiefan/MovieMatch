package use_case.review;

import java.util.ArrayList;
import java.util.List;

import entity.Review;

/**
 * Output data for loading reviews for one media item.
 */
public class GetMediaReviewsOutputData {
    private final List<Review> reviews;

    public GetMediaReviewsOutputData(final List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }

    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }
}
