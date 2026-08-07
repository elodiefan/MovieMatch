package use_case.delete_comment;

/**
 * Input data for deleting a comment.
 */
public final class DeleteCommentInputData {
    /** The comment id. */
    private final String commentId;
    /** The username. */
    private final String username;

    /**
     * Handles this review or comment operation.
     */
    public DeleteCommentInputData(final String inputCommentId,
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
