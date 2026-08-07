package use_case.create_review;

import use_case.get_media_reviews.ReviewSummaryData;

/**
 * Output boundary for creating a review.
 */
public interface CreateReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(ReviewSummaryData review);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
