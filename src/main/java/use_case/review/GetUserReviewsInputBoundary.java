package use_case.review;

/**
 * Input boundary for loading reviews written by one user.
 */
public interface GetUserReviewsInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(GetUserReviewsInputData inputData);
}
