package use_case.create_comment;

/**
 * Input boundary for creating a comment.
 */
public interface CreateCommentInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String reviewId, String parentCommentId,
                 String authorUsername, String authorDisplayName,
                 String commentText);
}
