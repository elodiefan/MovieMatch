package use_case.comment.edit_comment;

/**
 * Input data for editing a comment.
 */
public final class EditCommentInputData {
    /**
     * The comment id.
     */
    private final String commentId;
    /**
     * The username.
     */
    private final String username;
    /**
     * The updated comment text.
     */
    private final String commentText;

    /**
     * Creates input data for editing a comment.
     * @param inputCommentId the comment id
     * @param inputUsername the username
     * @param inputCommentText the updated comment text
     */
    public EditCommentInputData(final String inputCommentId,
                                final String inputUsername,
                                final String inputCommentText) {
        this.commentId = inputCommentId;
        this.username = inputUsername;
        this.commentText = inputCommentText;
    }

    /**
     * Returns the comment id.
     * @return the comment id
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * Returns the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the updated comment text.
     * @return the updated comment text
     */
    public String getCommentText() {
        return commentText;
    }
}
