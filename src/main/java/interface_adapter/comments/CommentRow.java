package interface_adapter.comments;

import java.time.ZonedDateTime;

/**
 * Display data for one comment.
 */
public final class CommentRow {
    /**
     * The comment id.
     */
    private final String commentId;
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
     * The created at.
     */
    private final ZonedDateTime createdAt;
    /**
     * The like count.
     */
    private final int likeCount;

    /**
     * Creates display data for one comment row.
     * @param inputCommentId the comment id
     * @param inputReviewId the review id
     * @param inputParentCommentId the parent comment id
     * @param inputAuthorUsername the author username
     * @param inputAuthorDisplayName the author display name
     * @param inputCommentText the comment text
     * @param inputCreatedAt the creation time
     * @param inputLikeCount the like count
     */
    public CommentRow(final String inputCommentId,
                      final String inputReviewId,
                      final String inputParentCommentId,
                      final String inputAuthorUsername,
                      final String inputAuthorDisplayName,
                      final String inputCommentText,
                      final ZonedDateTime inputCreatedAt,
                      final int inputLikeCount) {
        this.commentId = inputCommentId;
        this.reviewId = inputReviewId;
        this.parentCommentId = inputParentCommentId;
        this.authorUsername = inputAuthorUsername;
        this.authorDisplayName = inputAuthorDisplayName;
        this.commentText = inputCommentText;
        this.createdAt = inputCreatedAt;
        this.likeCount = inputLikeCount;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public String getCommentText() {
        return commentText;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
