package use_case.comment.like_comment;

/** Input data for liking a comment. */
public final class LikeCommentInputData {
    /** The comment id. */
    private final String commentId;
    /** The username. */
    private final String username;

    /** Handles this review or comment operation. */
    public LikeCommentInputData(final String inputCommentId,
                                final String inputUsername) {
        this.commentId = inputCommentId;
        this.username = inputUsername;
    }

    /** Handles this review or comment operation. */
    public String getCommentId() {
        return commentId;
    }
    /** Handles this review or comment operation. */
    public String getUsername() {
        return username;
    }
}
