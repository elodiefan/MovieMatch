package use_case.get_media_reviews;

import java.util.ArrayList;
import java.util.List;

import use_case.get_media_reviews.ReviewSummaryData;

/**
 * Output data for loading reviews for one media item.
 */
public final class GetMediaReviewsOutputData {
    /** The reviews. */
    private final List<ReviewSummaryData> reviews;

    /**
     * Handles this review or comment operation.
     */
    public GetMediaReviewsOutputData(final List<ReviewSummaryData>
                                             inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /**
     * Handles this review or comment operation.
     */
    public List<ReviewSummaryData> getReviews() {
        return new ArrayList<>(reviews);
    }
}
