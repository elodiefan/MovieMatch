package use_case.comment;

import java.util.List;

import entity.Comment;

/**
 * Interactor for unliking a comment.
 */
public class UnlikeCommentInteractor implements UnlikeCommentInputBoundary {
    private final UnlikeCommentDataAccessInterface commentDataAccessObject;
    private final UnlikeCommentOutputBoundary presenter;

    /**
     * Creates an unlike comment interactor without persistence.
     */
    public UnlikeCommentInteractor() {
        this(null, null);
    }

    /**
     * Creates an unlike comment interactor with persistence.
     * @param commentDataAccessObject the DAO used to unlike comments
     */
    public UnlikeCommentInteractor(
            final UnlikeCommentDataAccessInterface commentDataAccessObject) {
        this(commentDataAccessObject, null);
    }

    public UnlikeCommentInteractor(
            final UnlikeCommentDataAccessInterface commentDataAccessObject,
            final UnlikeCommentOutputBoundary presenter) {
        this.commentDataAccessObject = commentDataAccessObject;
        this.presenter = presenter;
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
     * @param commentId the id of the comment to unlike
     * @param username the username of the user unliking the comment
     * @return true if the comment was found and unliked
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
     * Removes a user's like from a comment.
     * @param commentId the id of the comment to unlike
     * @param username the username of the user unliking the comment
     * @param comments the comments to search through
     * @return true if the comment was found and unliked
     */
    private boolean unlikeComment(final String commentId, final String username,
                                 final List<Comment> comments) {
        final String trimmedCommentId = trimToEmpty(commentId);
        final String trimmedUsername = trimToEmpty(username);
        validateUnlikeCommentData(trimmedCommentId, trimmedUsername, comments);

        boolean unliked = false;
        for (Comment comment : comments) {
            if (isMatchingComment(comment, trimmedCommentId)) {
                comment.unlike(trimmedUsername);
                unliked = true;
            }
        }

        return unliked;
    }

    /**
     * Validates the data needed to unlike a persisted comment.
     * @param commentId the comment id to validate
     * @param username the username to validate
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
     * Checks whether a comment has the requested comment id.
     * @param comment the comment to check
     * @param commentId the comment id to match
     * @return true if the comment has the requested id
     */
    private boolean isMatchingComment(final Comment comment,
                                      final String commentId) {
        final boolean matchingComment;
        if (comment == null) {
            matchingComment = false;
        } else {
            matchingComment = comment.getCommentId().equals(commentId);
        }
        return matchingComment;
    }

    /**
     * Validates the data needed to unlike a comment.
     * @param commentId the comment id to validate
     * @param username the username to validate
     * @param comments the comment list to validate
     */
    private void validateUnlikeCommentData(final String commentId,
                                           final String username,
                                           final List<Comment> comments) {
        if (isBlank(commentId)) {
            throw new IllegalArgumentException("Comment id cannot be empty.");
        } else if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        } else if (comments == null) {
            throw new IllegalArgumentException("Comments cannot be null.");
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
