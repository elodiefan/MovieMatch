package use_case.comment.delete_comment;

/**
 * Input boundary for deleting a comment.
 */
public interface DeleteCommentInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(DeleteCommentInputData inputData);
}
