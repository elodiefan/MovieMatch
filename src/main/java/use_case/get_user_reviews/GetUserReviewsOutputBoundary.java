package use_case.get_user_reviews;

import java.util.List;

import use_case.get_media_reviews.ReviewSummaryData;

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
