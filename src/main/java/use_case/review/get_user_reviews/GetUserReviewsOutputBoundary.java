package use_case.review.get_user_reviews;

import java.util.List;

import use_case.review.ReviewSummaryData;

/**
 * Output boundary for loading reviews written by one user.
 */
public interface GetUserReviewsOutputBoundary {
    /**
     * Prepares the success view.
     */
    void prepareUserReviewsSuccessView(List<ReviewSummaryData> reviews);

    /**
     * Prepares the failure view.
     */
    String prepareFailView(String errorMessage);
}
