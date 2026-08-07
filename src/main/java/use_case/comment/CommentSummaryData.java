package use_case.comment;

import java.time.ZonedDateTime;

/**
 * Display-safe comment data shared by comment use cases.
 */
public final class CommentSummaryData {
    private final String commentId;
    private final String reviewId;
    private final String parentCommentId;
    private final String authorUsername;
    private final String authorDisplayName;
    private final String commentText;
    private final ZonedDateTime createdAt;
    private final int likeCount;

    /**
     * Creates display-safe comment data.
     */
    public CommentSummaryData(final String commentId, final String reviewId,
                              final String parentCommentId,
                              final String authorUsername,
                              final String authorDisplayName,
                              final String commentText,
                              final ZonedDateTime createdAt,
                              final int likeCount) {
        this.commentId = commentId;
        this.reviewId = reviewId;
        this.parentCommentId = parentCommentId;
        this.authorUsername = authorUsername;
        this.authorDisplayName = authorDisplayName;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
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
