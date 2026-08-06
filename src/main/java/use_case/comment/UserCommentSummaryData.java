package use_case.comment;

import java.time.ZonedDateTime;

/**
 * Summary data for one comment written by a user.
 */
public class UserCommentSummaryData {
    private final String commentId;
    private final String reviewId;
    private final String mediaTitle;
    private final String reviewText;
    private final String commentText;
    private final ZonedDateTime createdAt;
    private final int likeCount;

    /**
     * Creates summary data for one user comment.
     * @param commentId the comment id
     * @param reviewId the review id
     * @param mediaTitle the reviewed media title
     * @param reviewText the review text the user commented on
     * @param commentText the user's comment text
     * @param createdAt the comment creation time
     * @param likeCount the number of likes on the comment
     */
    public UserCommentSummaryData(final String commentId,
                                  final String reviewId,
                                  final String mediaTitle,
                                  final String reviewText,
                                  final String commentText,
                                  final ZonedDateTime createdAt,
                                  final int likeCount) {
        this.commentId = commentId;
        this.reviewId = reviewId;
        this.mediaTitle = mediaTitle;
        this.reviewText = reviewText;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    /**
     * Returns the comment id.
     * @return the comment id
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * Returns the review id.
     * @return the review id
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Returns the media title.
     * @return the media title
     */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /**
     * Returns the review text.
     * @return the review text
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Returns the comment text.
     * @return the comment text
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Returns the comment creation time.
     * @return the creation time
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the comment like count.
     * @return the like count
     */
    public int getLikeCount() {
        return likeCount;
    }
}
