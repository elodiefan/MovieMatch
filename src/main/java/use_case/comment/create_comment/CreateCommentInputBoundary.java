package use_case.comment.create_comment;

/**
 * Input boundary for creating a comment.
 */
public interface CreateCommentInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(CreateCommentInputData inputData);
}
