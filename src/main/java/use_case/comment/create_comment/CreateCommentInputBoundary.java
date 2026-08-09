package use_case.comment.create_comment;

/**
 * Input boundary for creating a comment.
 */
public interface CreateCommentInputBoundary {
    /**
     * Executes the use case.
     * @param reviewId the review id
     * @param parentCommentId the parent comment id, or null for a top-level comment
     * @param authorUsername the author's username
     * @param authorDisplayName the author's display name
     * @param commentText the comment text
     */
    void execute(String reviewId, String parentCommentId,
                 String authorUsername, String authorDisplayName,
                 String commentText);
}
