package use_case.comment.get_user_comments;

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
