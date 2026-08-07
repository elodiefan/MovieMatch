package use_case.review.get_user_reviews;

/**
 * Output boundary for loading reviews written by one user.
 */
public interface GetUserReviewsOutputBoundary {
    /**
     * Prepares the success view.
     */
    void prepareSuccessView(GetUserReviewsOutputData outputData);

    /**
     * Prepares the failure view.
     */
    String prepareFailView(String errorMessage);
}
