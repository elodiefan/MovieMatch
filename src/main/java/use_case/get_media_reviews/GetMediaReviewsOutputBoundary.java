package use_case.get_media_reviews;

import java.util.List;

import use_case.get_media_reviews.ReviewSummaryData;

/**
 * Output boundary for loading reviews for one media item.
 */
public interface GetMediaReviewsOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(List<ReviewSummaryData> reviews);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
