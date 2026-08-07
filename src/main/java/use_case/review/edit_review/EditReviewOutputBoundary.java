package use_case.review.edit_review;

import use_case.review.ReviewSummaryData;

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
