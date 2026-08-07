package use_case.review.get_media_reviews;

import java.util.ArrayList;
import java.util.List;

import entity.Review;
import use_case.review.ReviewSummaryData;
import use_case.review.ReviewSummaryMapper;

/**
 * Output data for loading reviews for one media item.
 */
public final class GetMediaReviewsOutputData {
    /** The reviews. */
    private final List<ReviewSummaryData> reviews;

    /**
     * Handles this review or comment operation.
     */
    public GetMediaReviewsOutputData(final List<Review> inputReviews) {
        this.reviews = ReviewSummaryMapper.toSummaries(inputReviews);
    }

    /**
     * Handles this review or comment operation.
     */
    public List<ReviewSummaryData> getReviews() {
        return new ArrayList<>(reviews);
    }
}
