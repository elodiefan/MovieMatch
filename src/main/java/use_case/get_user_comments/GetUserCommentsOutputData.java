package use_case.get_user_comments;

import java.util.ArrayList;
import java.util.List;
import java.time.ZonedDateTime;

/**
 * Output data for loading comments written by one user.
 */
public final class GetUserCommentsOutputData {
    /** The comments. */
    private final List<UserCommentData> comments;

    /**
     * Creates output data for loaded user comments.
     */
    public GetUserCommentsOutputData(
            final List<UserCommentData> inputComments) {
        this.comments = new ArrayList<>(inputComments);
    }

    /**
     * Returns loaded user comment summaries.
     */
    public List<UserCommentData> getComments() {
        return new ArrayList<>(comments);
    }

    /**
     * One comment row prepared by the get user comments use case.
     */
    public static final class UserCommentData {
        private final String commentId;
        private final String reviewId;
        private final String mediaTitle;
        private final String reviewText;
        private final String commentText;
        private final ZonedDateTime createdAt;
        private final int likeCount;

        /**
         * Creates one user comment row.
         */
        public UserCommentData(final String commentId, final String reviewId,
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
}
