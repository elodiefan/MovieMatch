package use_case.review.get_media_reviews;

import java.util.ArrayList;
import java.util.List;

import entity.Review;

/**
 * Output data for loading reviews for one media item.
 */
public final class GetMediaReviewsOutputData {
    /** The reviews. */
    private final List<Review> reviews;

    /**
     * Handles this review or comment operation.
     * @param inputReviews the inputReviews
     */
    public GetMediaReviewsOutputData(final List<Review> inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }
}
