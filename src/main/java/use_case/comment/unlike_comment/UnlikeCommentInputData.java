package use_case.comment.unlike_comment;

/**
 * Input data for unliking a comment.
 */
public final class UnlikeCommentInputData {
    /** The comment id. */
    private final String commentId;
    /** The username. */
    private final String username;

    /**
     * Handles this review or comment operation.
     */
    public UnlikeCommentInputData(final String inputCommentId,
                                  final String inputUsername) {
        this.commentId = inputCommentId;
        this.username = inputUsername;
    }

    /**
     * Handles this review or comment operation.
     */
    public String getCommentId() {
        return commentId;
    }
    /**
     * Handles this review or comment operation.
     */
    public String getUsername() {
        return username;
    }
}
