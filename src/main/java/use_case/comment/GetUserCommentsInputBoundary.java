package use_case.comment;

/**
 * Input boundary for loading comments written by one user.
 */
public interface GetUserCommentsInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(GetUserCommentsInputData inputData);
}
