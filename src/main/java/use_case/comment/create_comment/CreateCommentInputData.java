package use_case.comment.create_comment;

/**
 * Input data for creating a comment.
 */
public final class CreateCommentInputData {
    /**
     * The review id.
     */
    private final String reviewId;
    /**
     * The parent comment id.
     */
    private final String parentCommentId;
    /**
     * The author username.
     */
    private final String authorUsername;
    /**
     * The author display name.
     */
    private final String authorDisplayName;
    /**
     * The comment text.
     */
    private final String commentText;

    /**
     * Handles this review or comment operation.
     * @param inputReviewId the inputReviewId
     * @param inputParentCommentId the inputParentCommentId
     * @param inputAuthorUsername the inputAuthorUsername
     * @param inputAuthorDisplayName the inputAuthorDisplayName
     * @param inputCommentText the inputCommentText
     */
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

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getParentCommentId() {
        return parentCommentId;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getCommentText() {
        return commentText;
    }
}
