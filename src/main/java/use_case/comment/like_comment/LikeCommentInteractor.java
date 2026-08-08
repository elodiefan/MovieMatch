package use_case.comment.like_comment;

/**
 * Interactor for liking a comment.
 */
public final class LikeCommentInteractor implements LikeCommentInputBoundary {
    /**
     * The comment data access object.
     */
    private final LikeCommentDataAccessInterface commentDataAccessObject;
    /**
     * The presenter.
     */
    private final LikeCommentOutputBoundary presenter;

    /**
     * Creates a like comment interactor without persistence.
     */
    public LikeCommentInteractor() {
        this(null, null);
    }

    /**
     * Creates a like comment interactor with persistence.
     * @param inputCommentDataAccessObject the DAO used to like comments
     */
    public LikeCommentInteractor(
            final LikeCommentDataAccessInterface inputCommentDataAccessObject) {
        this(inputCommentDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputCommentDataAccessObject the inputCommentDataAccessObject
     * @param inputPresenter the inputPresenter
     */
    public LikeCommentInteractor(
            final LikeCommentDataAccessInterface inputCommentDataAccessObject,
            final LikeCommentOutputBoundary inputPresenter) {
        this.commentDataAccessObject = inputCommentDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final LikeCommentInputData inputData) {
        try {
            validatePresenter();
            final boolean liked = likeComment(inputData.getCommentId(),
                    inputData.getUsername());
            presenter.prepareSuccessView(new LikeCommentOutputData(liked));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Adds a user's like to a persisted comment.
     * @param commentId the id of the comment to like
     * @param username the username of the user liking the comment
     * @return true if the comment was found and liked
     */
    private boolean likeComment(final String commentId, final String username) {
        final String trimmedCommentId = trimToEmpty(commentId);
        final String trimmedUsername = trimToEmpty(username);
        validateLikeCommentData(trimmedCommentId, trimmedUsername);
        return commentDataAccessObject.likeComment(trimmedCommentId,
                trimmedUsername);
    }

    /**
     * Validates the data needed to like a persisted comment.
     * @param commentId the comment id to validate
     * @param username the username to validate
     */
    private void validateLikeCommentData(final String commentId,
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
                    "Like comment presenter has not been configured.");
        }
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
