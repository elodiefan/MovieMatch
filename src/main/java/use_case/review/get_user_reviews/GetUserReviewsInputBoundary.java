package use_case.review.get_user_reviews;

/**
 * Input boundary for loading reviews written by one user.
 */
public interface GetUserReviewsInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data for loading user reviews
     */
    void execute(GetUserReviewsInputData inputData);
}
