package use_case.edit_review;

import use_case.get_media_reviews.ReviewSummaryData;

/**
 * Output boundary for editing a review.
 */
public interface EditReviewOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(ReviewSummaryData review);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
