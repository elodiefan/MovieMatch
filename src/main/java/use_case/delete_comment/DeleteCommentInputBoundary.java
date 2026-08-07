package use_case.delete_comment;

/**
 * Input boundary for deleting a comment.
 */
public interface DeleteCommentInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String commentId, String username);
}
