package interface_adapter.comments;

import java.util.ArrayList;
import java.util.List;

import entity.Comment;
import use_case.comment.create_comment.CreateCommentOutputBoundary;
import use_case.comment.create_comment.CreateCommentOutputData;
import use_case.comment.delete_comment.DeleteCommentOutputBoundary;
import use_case.comment.delete_comment.DeleteCommentOutputData;
import use_case.comment.get_review_comments.GetReviewCommentsOutputBoundary;
import use_case.comment.get_review_comments.GetReviewCommentsOutputData;
import use_case.comment.like_comment.LikeCommentOutputBoundary;
import use_case.comment.like_comment.LikeCommentOutputData;
import use_case.comment.unlike_comment.UnlikeCommentOutputBoundary;
import use_case.comment.unlike_comment.UnlikeCommentOutputData;

/**
 * Presenter for review comments.
 */
public final class CommentsPresenter implements GetReviewCommentsOutputBoundary,
        CreateCommentOutputBoundary, DeleteCommentOutputBoundary,
        LikeCommentOutputBoundary, UnlikeCommentOutputBoundary {
    /**
     * The comments view model.
     */
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

}
