package use_case.unlike_comment;

/**
 * Input boundary for unliking a comment.
 */
public interface UnlikeCommentInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String commentId, String username);
}
