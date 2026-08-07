package use_case.get_review_comments;

import java.util.ArrayList;
import java.util.List;
import java.time.ZonedDateTime;

/**
 * Output data for loading comments on a review.
 */
public final class GetReviewCommentsOutputData {
    /** The review id. */
    private final String reviewId;
    /** The comments. */
    private final List<ReviewCommentData> comments;

    /**
     * Handles this review or comment operation.
     */
    public GetReviewCommentsOutputData(final String inputReviewId,
                                       final List<ReviewCommentData>
                                               inputComments) {
        this.reviewId = inputReviewId;
        this.comments = new ArrayList<>(inputComments);
    }

    /**
     * Handles this review or comment operation.
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Handles this review or comment operation.
     */
    public List<ReviewCommentData> getComments() {
        return new ArrayList<>(comments);
    }

    /**
     * One comment row prepared by the get review comments use case.
     */
    public static final class ReviewCommentData {
        private final String commentId;
        private final String reviewId;
        private final String parentCommentId;
        private final String authorUsername;
        private final String authorDisplayName;
        private final String commentText;
        private final ZonedDateTime createdAt;
        private final int likeCount;

        /**
         * Creates one review comment row.
         */
        public ReviewCommentData(final String commentId,
                                 final String reviewId,
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
}
