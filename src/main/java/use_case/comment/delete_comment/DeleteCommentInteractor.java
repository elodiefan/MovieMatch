package use_case.comment.delete_comment;

import java.util.Optional;

import entity.Comment;

/**
 * Interactor for deleting a comment.
 */
public final class DeleteCommentInteractor
        implements DeleteCommentInputBoundary {
    /** The comment data access object. */
    private final DeleteCommentDataAccessInterface commentDataAccessObject;
    /** The presenter. */
    private final DeleteCommentOutputBoundary presenter;

    /**
     * Creates a delete comment interactor without persistence.
     */
    public DeleteCommentInteractor() {
        this(null, null);
    }

    /**
     * Creates a delete comment interactor with persistence.
     * @param inputCommentDataAccessObject the DAO used to delete comments
     */
    public DeleteCommentInteractor(
            final DeleteCommentDataAccessInterface
                    inputCommentDataAccessObject) {
        this(inputCommentDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputCommentDataAccessObject the inputCommentDataAccessObject
     * @param inputPresenter the inputPresenter
     */
    public DeleteCommentInteractor(
            final DeleteCommentDataAccessInterface inputCommentDataAccessObject,
            final DeleteCommentOutputBoundary inputPresenter) {
        this.commentDataAccessObject = inputCommentDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final DeleteCommentInputData inputData) {
        try {
            validatePresenter();
            final boolean deleted = deleteComment(inputData.getCommentId(),
                    inputData.getUsername());
            if (deleted) {
                presenter.prepareSuccessView(
                        new DeleteCommentOutputData(true));
            } else {
                presenter.prepareFailView("Comment could not be deleted.");
            }
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Deletes one persisted comment written by the given user.
     * @param commentId the id of the comment to delete
     * @param username the username of the user deleting the comment
     * @return true if the comment was deleted
     */
    private boolean deleteComment(final String commentId,
                                 final String username) {
        final String trimmedCommentId = trimToEmpty(commentId);
        final String trimmedUsername = trimToEmpty(username);
        validateDeleteCommentData(trimmedCommentId, trimmedUsername);

        final Optional<Comment> comment =
                commentDataAccessObject.getCommentById(trimmedCommentId);
        final boolean deleted;
        if (comment.isPresent()
                && canDeleteComment(comment.get(), trimmedCommentId,
                trimmedUsername)) {
            deleted = commentDataAccessObject.deleteComment(trimmedCommentId);
        } else {
            deleted = false;
        }
        return deleted;
    }

    /**
     * Validates data needed to delete a persisted comment.
     * @param commentId the comment id to validate
     * @param username the username to validate
     */
    private void validateDeleteCommentData(final String commentId,
                                           final String username) {
        if (isBlank(commentId)) {
            throw new IllegalArgumentException("Comment id cannot be empty.");
        } else if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        } else if (commentDataAccessObject == null) {
            throw new IllegalStateException(
                    "Comment data access object has not been configured.");
        }
    }

    private void validatePresenter() {
        if (presenter == null) {
            throw new IllegalStateException(
                    "Delete comment presenter has not been configured.");
        }
    }

    /**
     * Checks whether the comment can be deleted by the user.
     * @param comment the comment to check
     * @param commentId the id of the comment to delete
     * @param username the username of the user deleting the comment
     * @return true if the comment matches the id and author
     */
    private boolean canDeleteComment(final Comment comment,
                                     final String commentId,
                                     final String username) {
        final boolean canDelete;
        if (comment == null) {
            canDelete = false;
        } else {
            canDelete = comment.getCommentId().equals(commentId)
                    && comment.getAuthorUsername().equals(username);
        }
        return canDelete;
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
     * Trims a text value, or returns an empty string if it is null.
     * @param value the value to trim
     * @return the trimmed value
     */
    private String trimToEmpty(final String value) {
        final String trimmedValue;
        if (value == null) {
            trimmedValue = "";
        } else {
            trimmedValue = value.trim();
        }
        return trimmedValue;
    }
}
