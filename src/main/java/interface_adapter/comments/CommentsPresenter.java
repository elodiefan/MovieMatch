package interface_adapter.comments;

import java.util.ArrayList;
import java.util.List;

import use_case.get_review_comments.CommentSummaryData;
import use_case.create_comment.CreateCommentOutputBoundary;
import use_case.delete_comment.DeleteCommentOutputBoundary;
import use_case.get_review_comments.GetReviewCommentsOutputBoundary;
import use_case.like_comment.LikeCommentOutputBoundary;
import use_case.unlike_comment.UnlikeCommentOutputBoundary;

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
     */
    public CommentsPresenter(final CommentsViewModel inputCommentsViewModel) {
        this.commentsViewModel = inputCommentsViewModel;
    }

    @Override
    public void prepareSuccessView(final String reviewId,
                                   final List<CommentSummaryData> comments) {
        final CommentsState state = commentsViewModel.getState();
        final List<CommentRow> commentRows = state.getComments();
        commentRows.removeIf(comment -> comment.getReviewId().equals(
                reviewId));
        commentRows.addAll(prepareComments(comments));
        state.setComments(commentRows);
        state.setCommentsError(null);
        commentsViewModel.setState(state);
        commentsViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(final boolean created) {
        clearError();
    }

    /**
     * Converts comment summaries into rows that can be displayed by the comments
     * view.
     */
    private List<CommentRow> prepareComments(
            final List<CommentSummaryData> comments) {
        final List<CommentRow> commentRows = new ArrayList<>();
        if (comments != null) {
            for (CommentSummaryData comment : comments) {
                if (comment != null) {
                    commentRows.add(createCommentRow(comment));
                }
            }
        }
        return commentRows;
    }

    /**
     * Converts an error message into display-safe text.
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
     * Converts one comment summary into one displayed row.
     */
    private CommentRow createCommentRow(final CommentSummaryData comment) {
        return new CommentRow(comment.getCommentId(), comment.getReviewId(),
                comment.getParentCommentId(), comment.getAuthorUsername(),
                comment.getAuthorDisplayName(), comment.getCommentText(),
                comment.getCreatedAt(), comment.getLikeCount());
    }

    /**
     * Checks whether a text value is empty or only whitespace.
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

}
