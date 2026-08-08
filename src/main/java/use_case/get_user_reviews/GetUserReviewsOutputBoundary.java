package use_case.get_user_reviews;

/**
 * Output boundary for loading reviews written by one user.
 */
public interface GetUserReviewsOutputBoundary {
    /**
     * Prepares the success view.
     * @param outputData the output data
     */
    void prepareUserReviewsSuccessView(GetUserReviewsOutputData outputData);

    /**
     * Prepares the failure view.
     * @param errorMessage the error message
     * @return the display-safe error message
     */
    String prepareFailView(String errorMessage);
}
