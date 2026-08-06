package interface_adapter.user_reviews;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * State for the user reviews view.
 */
public final class UserReviewsState {
    /** The username. */
    private String username = "";
    /** The reviews. */
    private List<UserReviewsPresenter.UserReviewRow> reviews =
            new ArrayList<>();
    /** The comments. */
    private List<CommentRow> comments = new ArrayList<>();
    /** The selected review id. */
    private String selectedReviewId = "";
    /** The user reviews error. */
    private String userReviewsError;

    /**
     * Returns the username whose reviews are displayed.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username whose reviews are displayed.
     * @param inputUsername the username
     */
    public void setUsername(final String inputUsername) {
        this.username = inputUsername;
    }

    /**
     * Returns the review rows displayed in the view.
     * @return a copy of the review rows
     */
    public List<UserReviewsPresenter.UserReviewRow> getReviews() {
        return new ArrayList<>(reviews);
    }

    /**
     * Sets the review rows displayed in the view.
     * @param inputReviews the review rows
     */
    public void setReviews(
            final List<UserReviewsPresenter.UserReviewRow> inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /**
     * Returns the comment rows displayed in the view.
     * @return a copy of the comment rows
     */
    public List<CommentRow> getComments() {
        return new ArrayList<>(comments);
    }

    /**
     * Sets the comment rows displayed in the view.
     * @param inputComments the comment rows
     */
    public void setComments(final List<CommentRow> inputComments) {
        this.comments = new ArrayList<>(inputComments);
    }

    /**
     * Returns the selected review id.
     * @return the selected review id
     */
    public String getSelectedReviewId() {
        return selectedReviewId;
    }

    /**
     * Sets the selected review id.
     * @param inputSelectedReviewId the selected review id
     */
    public void setSelectedReviewId(final String inputSelectedReviewId) {
        this.selectedReviewId = inputSelectedReviewId;
    }

    /**
     * Returns the current user reviews error message.
     * @return the error message
     */
    public String getUserReviewsError() {
        return userReviewsError;
    }

    /**
     * Sets the current user reviews error message.
     * @param inputUserReviewsError the error message
     */
    public void setUserReviewsError(final String inputUserReviewsError) {
        this.userReviewsError = inputUserReviewsError;
    }

    /**
     * Display data for one comment in the user's comment history.
     */
    public static final class CommentRow {
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

        /**
         * Creates display data for one comment row.
         * @param comment the comment summary to present
         */
        public CommentRow(
                final use_case.comment.UserCommentSummaryData comment) {
            this.commentId = comment.getCommentId();
            this.reviewId = comment.getReviewId();
            this.mediaTitle = comment.getMediaTitle();
            this.reviewText = comment.getReviewText();
            this.commentText = comment.getCommentText();
            this.createdAt = comment.getCreatedAt();
            this.likeCount = comment.getLikeCount();
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
         * Returns the review text the user commented on.
         * @return the review text
         */
        public String getReviewText() {
            return reviewText;
        }

        /**
         * Returns the user's comment text.
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
}
