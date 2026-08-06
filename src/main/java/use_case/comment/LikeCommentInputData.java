package use_case.comment;

/**
 * Input data for liking a comment.
 */
public class LikeCommentInputData {
    private final String commentId;
    private final String username;

    public LikeCommentInputData(final String commentId, final String username) {
        this.commentId = commentId;
        this.username = username;
    }

    public String getCommentId() { return commentId; }
    public String getUsername() { return username; }
}
