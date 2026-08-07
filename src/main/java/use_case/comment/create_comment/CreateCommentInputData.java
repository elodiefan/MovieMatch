package use_case.comment.create_comment;

/** Input data for creating a comment. */
public final class CreateCommentInputData {
    /** The review id. */
    private final String reviewId;
    /** The parent comment id. */
    private final String parentCommentId;
    /** The author username. */
    private final String authorUsername;
    /** The author display name. */
    private final String authorDisplayName;
    /** The comment text. */
    private final String commentText;

    /** Handles this review or comment operation. */
    public CreateCommentInputData(final String inputReviewId,
                                  final String inputParentCommentId,
                                  final String inputAuthorUsername,
                                  final String inputAuthorDisplayName,
                                  final String inputCommentText) {
        this.reviewId = inputReviewId;
        this.parentCommentId = inputParentCommentId;
        this.authorUsername = inputAuthorUsername;
        this.authorDisplayName = inputAuthorDisplayName;
        this.commentText = inputCommentText;
    }

    /** Handles this review or comment operation. */
    public String getReviewId() {
        return reviewId;
    }
    /** Handles this review or comment operation. */
    public String getParentCommentId() {
        return parentCommentId;
    }
    /** Handles this review or comment operation. */
    public String getAuthorUsername() {
        return authorUsername;
    }
    /** Handles this review or comment operation. */
    public String getAuthorDisplayName() {
        return authorDisplayName;
    }
    /** Handles this review or comment operation. */
    public String getCommentText() {
        return commentText;
    }
}
