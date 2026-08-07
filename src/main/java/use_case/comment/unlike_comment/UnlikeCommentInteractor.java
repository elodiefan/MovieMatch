package use_case.comment.unlike_comment;

/**
 * Interactor for unliking a comment.
 */
public final class UnlikeCommentInteractor
        implements UnlikeCommentInputBoundary {
    /** The comment data access object. */
    private final UnlikeCommentDataAccessInterface commentDataAccessObject;
    /** The presenter. */
    private final UnlikeCommentOutputBoundary presenter;

    /**
     * Creates an unlike comment interactor without persistence.
     */
    public UnlikeCommentInteractor() {
        this(null, null);
    }

    /**
     * Creates an unlike comment interactor with persistence.
     */
    public UnlikeCommentInteractor(
            final UnlikeCommentDataAccessInterface
                    inputCommentDataAccessObject) {
        this(inputCommentDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     */
    public UnlikeCommentInteractor(
            final UnlikeCommentDataAccessInterface inputCommentDataAccessObject,
            final UnlikeCommentOutputBoundary inputPresenter) {
        this.commentDataAccessObject = inputCommentDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final UnlikeCommentInputData inputData) {
        try {
            validatePresenter();
            final boolean unliked = unlikeComment(inputData.getCommentId(),
                    inputData.getUsername());
            presenter.prepareSuccessView(new UnlikeCommentOutputData(unliked));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Removes a user's like from a persisted comment.
     */
    private boolean unlikeComment(final String commentId,
                                 final String username) {
        final String trimmedCommentId = trimToEmpty(commentId);
        final String trimmedUsername = trimToEmpty(username);
        validateUnlikeCommentData(trimmedCommentId, trimmedUsername);
        return commentDataAccessObject.unlikeComment(trimmedCommentId,
                trimmedUsername);
    }

    /**
     * Validates the data needed to unlike a persisted comment.
     */
    private void validateUnlikeCommentData(final String commentId,
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
                    "Unlike comment presenter has not been configured.");
        }
    }

    /**
     * Checks whether a text value is empty or only whitespace.
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Trims a text value, or returns an empty string if it is null.
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
