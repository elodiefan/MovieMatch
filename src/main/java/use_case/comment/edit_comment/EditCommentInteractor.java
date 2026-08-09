package use_case.comment.edit_comment;

import java.util.Optional;

import entity.Comment;

/**
 * Interactor for editing a comment.
 */
public final class EditCommentInteractor implements EditCommentInputBoundary {
    /**
     * The comment data access object.
     */
    private final EditCommentDataAccessInterface commentDataAccessObject;
    /**
     * The presenter.
     */
    private final EditCommentOutputBoundary presenter;

    /**
     * Creates an edit comment interactor.
     * @param inputCommentDataAccessObject the DAO used to edit comments
     * @param inputPresenter the output boundary
     */
    public EditCommentInteractor(
            final EditCommentDataAccessInterface inputCommentDataAccessObject,
            final EditCommentOutputBoundary inputPresenter) {
        this.commentDataAccessObject = inputCommentDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final String commentId, final String username,
                        final String newCommentText) {
        try {
            validatePresenter();
            final EditCommentInputData inputData =
                    new EditCommentInputData(commentId, username,
                            newCommentText);
            final boolean edited = editComment(inputData.getCommentId(),
                    inputData.getUsername(), inputData.getCommentText());
            if (edited) {
                presenter.prepareSuccessView(true);
            } else {
                presenter.prepareFailView("Comment could not be edited.");
            }
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    private boolean editComment(final String commentId,
                                final String username,
                                final String newCommentText) {
        final String trimmedCommentId = trimToEmpty(commentId);
        final String trimmedUsername = trimToEmpty(username);
        final String trimmedCommentText = trimToEmpty(newCommentText);
        validateEditCommentData(trimmedCommentId, trimmedUsername,
                trimmedCommentText);

        final Optional<Comment> comment =
                commentDataAccessObject.getCommentById(trimmedCommentId);
        final boolean edited;
        if (comment.isPresent()
                && comment.get().getAuthorUsername().equals(trimmedUsername)) {
            edited = commentDataAccessObject.editComment(trimmedCommentId,
                    trimmedCommentText);
        } else {
            edited = false;
        }
        return edited;
    }

    private void validateEditCommentData(final String commentId,
                                         final String username,
                                         final String commentText) {
        if (isBlank(commentId)) {
            throw new IllegalArgumentException("Comment id cannot be empty.");
        } else if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        } else if (isBlank(commentText)) {
            throw new IllegalArgumentException("Comment text cannot be empty.");
        } else if (commentDataAccessObject == null) {
            throw new IllegalStateException(
                    "Comment data access object has not been configured.");
        }
    }

    private void validatePresenter() {
        if (presenter == null) {
            throw new IllegalStateException(
                    "Edit comment presenter has not been configured.");
        }
    }

    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

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
