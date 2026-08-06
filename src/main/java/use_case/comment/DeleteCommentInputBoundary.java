package use_case.comment;

/**
 * Input boundary for deleting a comment.
 */
public interface DeleteCommentInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(DeleteCommentInputData inputData);
}
