package use_case.get_review_comments;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
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
     * Creates output data for loaded review comments.
     * @param inputReviewId the review id
     * @param inputComments the loaded comments
     */
    public GetReviewCommentsOutputData(final String inputReviewId,
                                       final List<ReviewCommentData>
                                               inputComments) {
        this.reviewId = inputReviewId;
        this.comments = new ArrayList<>(inputComments);
    }

    /**
     * Returns the review id.
     * @return the review id
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Returns the loaded review comments.
     * @return the loaded review comments
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
        private final Set<String> likedByUsernames;

        /**
         * Creates one review comment row.
         * @param commentId the comment id
         * @param reviewId the review id
         * @param parentCommentId the parent comment id
         * @param authorUsername the author's username
         * @param authorDisplayName the author's display name
         * @param commentText the comment text
         * @param createdAt when the comment was created
         * @param likeCount the number of likes
         * @param likedByUsernames usernames that liked the comment
         */
        public ReviewCommentData(final String commentId,
                                 final String reviewId,
                                 final String parentCommentId,
                                 final String authorUsername,
                                 final String authorDisplayName,
                                 final String commentText,
                                 final ZonedDateTime createdAt,
                                 final int likeCount,
                                 final Set<String> likedByUsernames) {
            this.commentId = commentId;
            this.reviewId = reviewId;
            this.parentCommentId = parentCommentId;
            this.authorUsername = authorUsername;
            this.authorDisplayName = authorDisplayName;
            this.commentText = commentText;
            this.createdAt = createdAt;
            this.likeCount = likeCount;
            this.likedByUsernames = new HashSet<>(likedByUsernames);
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

        public Set<String> getLikedByUsernames() {
            return new HashSet<>(likedByUsernames);
        }
    }
}
