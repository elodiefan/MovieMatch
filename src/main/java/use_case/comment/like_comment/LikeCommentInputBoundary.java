package use_case.comment.like_comment;

/**
 * Input boundary for liking a comment.
 */
public interface LikeCommentInputBoundary {
    /**
     * Executes the use case.
     * @param commentId the comment id
     * @param username the username liking the comment
     */
    void execute(String commentId, String username);
}
