package use_case.comment;

import java.time.ZonedDateTime;

/** Summary data for one comment written by a user. */
public final class UserCommentSummaryData {
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

    /** Creates summary data for one user comment. */
    public UserCommentSummaryData(final String inputCommentId,
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

    /** Returns the comment id. */
    public String getCommentId() {
        return commentId;
    }

    /** Returns the review id. */
    public String getReviewId() {
        return reviewId;
    }

    /** Returns the media title. */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /** Returns the review text. */
    public String getReviewText() {
        return reviewText;
    }

    /** Returns the comment text. */
    public String getCommentText() {
        return commentText;
    }

    /** Returns the comment creation time. */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /** Returns the comment like count. */
    public int getLikeCount() {
        return likeCount;
    }
}
