package use_case.comment.delete_comment;

/**
 * Input boundary for deleting a comment.
 */
public interface DeleteCommentInputBoundary {
    /**
     * Executes the use case.
     * @param commentId the comment id
     * @param username the username requesting deletion
     */
    void execute(String commentId, String username);
}
