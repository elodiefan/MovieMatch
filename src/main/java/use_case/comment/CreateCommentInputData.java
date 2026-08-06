package use_case.comment;

/**
 * Input data for creating a comment.
 */
public class CreateCommentInputData {
    private final String reviewId;
    private final String parentCommentId;
    private final String authorUsername;
    private final String authorDisplayName;
    private final String commentText;

    public CreateCommentInputData(final String reviewId,
                                  final String parentCommentId,
                                  final String authorUsername,
                                  final String authorDisplayName,
                                  final String commentText) {
        this.reviewId = reviewId;
        this.parentCommentId = parentCommentId;
        this.authorUsername = authorUsername;
        this.authorDisplayName = authorDisplayName;
        this.commentText = commentText;
    }

    public String getReviewId() { return reviewId; }
    public String getParentCommentId() { return parentCommentId; }
    public String getAuthorUsername() { return authorUsername; }
    public String getAuthorDisplayName() { return authorDisplayName; }
    public String getCommentText() { return commentText; }
}
