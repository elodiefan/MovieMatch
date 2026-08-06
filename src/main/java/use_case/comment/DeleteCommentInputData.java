package use_case.comment;

/**
 * Input data for deleting a comment.
 */
public class DeleteCommentInputData {
    private final String commentId;
    private final String username;

    public DeleteCommentInputData(final String commentId,
                                  final String username) {
        this.commentId = commentId;
        this.username = username;
    }

    public String getCommentId() { return commentId; }
    public String getUsername() { return username; }
}
