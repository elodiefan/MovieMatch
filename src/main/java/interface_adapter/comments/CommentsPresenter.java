package interface_adapter.comments;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Comment;
import use_case.comment.CreateCommentOutputBoundary;
import use_case.comment.CreateCommentOutputData;
import use_case.comment.DeleteCommentOutputBoundary;
import use_case.comment.DeleteCommentOutputData;
import use_case.comment.GetReviewCommentsOutputBoundary;
import use_case.comment.GetReviewCommentsOutputData;
import use_case.comment.LikeCommentOutputBoundary;
import use_case.comment.LikeCommentOutputData;
import use_case.comment.UnlikeCommentOutputBoundary;
import use_case.comment.UnlikeCommentOutputData;

/**
 * Presenter for review comments.
 */
public final class CommentsPresenter implements GetReviewCommentsOutputBoundary,
        CreateCommentOutputBoundary, DeleteCommentOutputBoundary,
        LikeCommentOutputBoundary, UnlikeCommentOutputBoundary {
    /** The comments view model. */
    private final CommentsViewModel commentsViewModel;

    /**
     * Creates a presenter used only for row conversion.
     */
    public CommentsPresenter() {
        this(null);
    }

    /**
     * Creates a presenter for the comments view model.
     * @param inputCommentsViewModel the view model to update
     */
    public CommentsPresenter(final CommentsViewModel inputCommentsViewModel) {
        this.commentsViewModel = inputCommentsViewModel;
    }

    @Override
    public void prepareSuccessView(
            final GetReviewCommentsOutputData outputData) {
        final CommentsState state = commentsViewModel.getState();
        final List<CommentRow> commentRows = state.getComments();
        commentRows.removeIf(comment -> comment.getReviewId().equals(
                outputData.getReviewId()));
        commentRows.addAll(prepareComments(outputData.getComments()));
        state.setComments(commentRows);
        state.setCommentsError(null);
        commentsViewModel.setState(state);
        commentsViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(final CreateCommentOutputData outputData) {
        clearError();
    }

    @Override
    public void prepareSuccessView(final DeleteCommentOutputData outputData) {
        clearError();
    }

    @Override
    public void prepareSuccessView(final LikeCommentOutputData outputData) {
        clearError();
    }

    @Override
    public void prepareSuccessView(final UnlikeCommentOutputData outputData) {
        clearError();
    }

    /**
     * Converts comment entities into rows that can be displayed by the comments
     * view.
     * @param comments the comments to present
     * @return display-safe comment rows
     */
    private List<CommentRow> prepareComments(final List<Comment> comments) {
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
        if (commentsViewModel != null) {
            final CommentsState state = commentsViewModel.getState();
            state.setCommentsError(displayError);
            commentsViewModel.setState(state);
            commentsViewModel.firePropertyChanged();
        }
        return displayError;
    }

    private void clearError() {
        if (commentsViewModel != null) {
            final CommentsState state = commentsViewModel.getState();
            state.setCommentsError(null);
            commentsViewModel.setState(state);
            commentsViewModel.firePropertyChanged();
        }
    }

    /**
     * Converts one comment entity into one displayed row.
     * @param comment the comment to convert
     * @return the displayed comment row
     */
    private CommentRow createCommentRow(final Comment comment) {
        return new CommentRow(comment);
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
        /** The comment id. */
        private final String commentId;
        /** The review id. */
        private final String reviewId;
        /** The parent comment id. */
        private final String parentCommentId;
        /** The author username. */
        private final String authorUsername;
        /** The author display name. */
        private final String authorDisplayName;
        /** The comment text. */
        private final String commentText;
        /** The created at. */
        private final ZonedDateTime createdAt;
        /** The like count. */
        private final int likeCount;

        /**
         * Creates display data for one comment row.
         * @param comment the comment to present
         */
        public CommentRow(final Comment comment) {
            this.commentId = comment.getCommentId();
            this.reviewId = comment.getReviewId();
            this.parentCommentId = comment.getParentCommentId();
            this.authorUsername = comment.getAuthorUsername();
            this.authorDisplayName = comment.getAuthorDisplayName();
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
