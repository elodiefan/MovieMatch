package use_case.comment;

/**
 * Input boundary for loading comments on a review.
 */
public interface GetReviewCommentsInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(GetReviewCommentsInputData inputData);
}
