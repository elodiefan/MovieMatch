package use_case.comment.unlike_comment;

/**
 * Input boundary for unliking a comment.
 */
public interface UnlikeCommentInputBoundary {
    /**
     * Executes the use case.
     * @param commentId the comment id
     * @param username the username unliking the comment
     */
    void execute(String commentId, String username);
}
