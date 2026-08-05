package interface_adapter.user_reviews;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * State for the user reviews view.
 */
public class UserReviewsState {
    private String username = "";
    private List<UserReviewsPresenter.UserReviewRow> reviews = new ArrayList<>();
    private List<CommentRow> comments = new ArrayList<>();
    private String selectedReviewId = "";
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
     * @param username the username
     */
    public void setUsername(final String username) {
        this.username = username;
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
     * @param reviews the review rows
     */
    public void setReviews(
            final List<UserReviewsPresenter.UserReviewRow> reviews) {
        this.reviews = new ArrayList<>(reviews);
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
     * @param comments the comment rows
     */
    public void setComments(final List<CommentRow> comments) {
        this.comments = new ArrayList<>(comments);
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
     * @param selectedReviewId the selected review id
     */
    public void setSelectedReviewId(final String selectedReviewId) {
        this.selectedReviewId = selectedReviewId;
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
     * @param userReviewsError the error message
     */
    public void setUserReviewsError(final String userReviewsError) {
        this.userReviewsError = userReviewsError;
    }

    /**
     * Display data for one comment in the user's comment history.
     */
    public static final class CommentRow {
        private final String mediaTitle;
        private final String reviewText;
        private final String commentText;
        private final ZonedDateTime createdAt;

        /**
         * Creates display data for one comment row.
         * @param mediaTitle the media title
         * @param reviewText the review text the user commented on
         * @param commentText the user's comment text
         * @param createdAt the comment creation time
         */
        public CommentRow(final String mediaTitle, final String reviewText,
                          final String commentText,
                          final ZonedDateTime createdAt) {
            this.mediaTitle = mediaTitle;
            this.reviewText = reviewText;
            this.commentText = commentText;
            this.createdAt = createdAt;
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
    }
}
