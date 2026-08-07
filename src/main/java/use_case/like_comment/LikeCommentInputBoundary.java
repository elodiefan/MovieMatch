package use_case.like_comment;

/**
 * Input boundary for liking a comment.
 */
public interface LikeCommentInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String commentId, String username);
}
