package interface_adapter.comments;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Comment;

/**
 * Presenter for review comments.
 */
public class CommentsPresenter {
    /**
     * Converts comment entities into rows that can be displayed by the comments
     * view.
     * @param comments the comments to present
     * @return display-safe comment rows
     */
    public List<CommentRow> prepareComments(final List<Comment> comments) {
        final List<CommentRow> commentRows = new ArrayList<>();
        if (comments != null) {
            for (Comment comment : comments) {
                if (comment != null) {
                    commentRows.add(createCommentRow(comment));
                }
            }
        }
        return commentRows;
    }

    /**
     * Converts an error message into display-safe text.
     * @param errorMessage the error message to present
     * @return the display-safe error message
     */
    public String prepareFailView(final String errorMessage) {
        final String displayError;
        if (isBlank(errorMessage)) {
            displayError = "Unable to load comments.";
        } else {
            displayError = errorMessage.trim();
        }
        return displayError;
    }

    /**
     * Converts one comment entity into one displayed row.
     * @param comment the comment to convert
     * @return the displayed comment row
     */
    private CommentRow createCommentRow(final Comment comment) {
        return new CommentRow(comment.getCommentId(), comment.getReviewId(),
                comment.getParentCommentId(), comment.getAuthorUsername(),
                comment.getAuthorDisplayName(), comment.getCommentText(),
                comment.getCreatedAt(), comment.getLikeCount());
    }

    /**
     * Checks whether a text value is empty or only whitespace.
     * @param value the value to check
     * @return true if the value is blank
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Display data for one comment.
     */
    public static final class CommentRow {
        private final String commentId;
        private final String reviewId;
        private final String parentCommentId;
        private final String authorUsername;
        private final String authorDisplayName;
        private final String commentText;
        private final ZonedDateTime createdAt;
        private final int likeCount;

        /**
         * Creates display data for one comment row.
         * @param commentId the comment id
         * @param reviewId the review id
         * @param parentCommentId the parent comment id
         * @param authorUsername the author's username
         * @param authorDisplayName the author's display name
         * @param commentText the comment text
         * @param createdAt the comment creation time
         * @param likeCount the number of likes on the comment
         */
        public CommentRow(final String commentId, final String reviewId,
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
         * Returns the parent comment id.
         * @return the parent comment id
         */
        public String getParentCommentId() {
            return parentCommentId;
        }

        /**
         * Returns the author's username.
         * @return the author's username
         */
        public String getAuthorUsername() {
            return authorUsername;
        }

        /**
         * Returns the author's display name.
         * @return the author's display name
         */
        public String getAuthorDisplayName() {
            return authorDisplayName;
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
}
