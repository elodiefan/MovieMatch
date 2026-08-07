package interface_adapter.user_reviews;

import java.time.ZonedDateTime;

/** Display data for one comment in the user's comment history. */
public final class UserCommentRow {
    /** The comment id. */
    private final String commentId;
    /** The review id. */
    private final String reviewId;
    /** The media title. */
    private final String mediaTitle;
    /** The review text. */
    private final String reviewText;
    /** The comment text. */
    private final String commentText;
    /** The created at. */
    private final ZonedDateTime createdAt;
    /** The like count. */
    private final int likeCount;

    /** Creates display data for one comment row. */
    public UserCommentRow(final String inputCommentId,
                          final String inputReviewId,
                          final String inputMediaTitle,
                          final String inputReviewText,
                          final String inputCommentText,
                          final ZonedDateTime inputCreatedAt,
                          final int inputLikeCount) {
        this.commentId = inputCommentId;
        this.reviewId = inputReviewId;
        this.mediaTitle = inputMediaTitle;
        this.reviewText = inputReviewText;
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

    public String getMediaTitle() {
        return mediaTitle;
    }

    public String getReviewText() {
        return reviewText;
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
