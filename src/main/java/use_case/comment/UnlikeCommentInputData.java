package use_case.comment;

/**
 * Input data for unliking a comment.
 */
public class UnlikeCommentInputData {
    private final String commentId;
    private final String username;

    public UnlikeCommentInputData(final String commentId,
                                  final String username) {
        this.commentId = commentId;
        this.username = username;
    }

    public String getCommentId() { return commentId; }
    public String getUsername() { return username; }
}
