package use_case.comment;

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
     * @param inputCommentId the inputCommentId
     * @param inputUsername the inputUsername
     */
    public DeleteCommentInputData(final String inputCommentId,
                                  final String inputUsername) {
        this.commentId = inputCommentId;
        this.username = inputUsername;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getCommentId() {
        return commentId;
    }
    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getUsername() {
        return username;
    }
}
