package use_case.comment.create_comment;

import java.util.HashSet;
import java.util.UUID;

import entity.Comment;
import entity.UserContent;

/**
 * Interactor for creating a comment.
 */
public final class CreateCommentInteractor
        implements CreateCommentInputBoundary {
    /**
     * The comment data access object.
     */
    private final CreateCommentDataAccessInterface commentDataAccessObject;
    /**
     * The presenter.
     */
    private final CreateCommentOutputBoundary presenter;

    /**
     * Creates a comment interactor without persistence.
     */
    public CreateCommentInteractor() {
        this(null, null);
    }

    /**
     * Creates a comment interactor with persistence.
     * @param inputCommentDataAccessObject the DAO used to save comments
     */
    public CreateCommentInteractor(
            final CreateCommentDataAccessInterface
                    inputCommentDataAccessObject) {
        this(inputCommentDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputCommentDataAccessObject the inputCommentDataAccessObject
     * @param inputPresenter the inputPresenter
     */
    public CreateCommentInteractor(
            final CreateCommentDataAccessInterface inputCommentDataAccessObject,
            final CreateCommentOutputBoundary inputPresenter) {
        this.commentDataAccessObject = inputCommentDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final String reviewId, final String parentCommentId,
                        final String authorUsername,
                        final String authorDisplayName,
                        final String commentText) {
        try {
            validatePresenter();
            final CreateCommentInputData inputData =
                    new CreateCommentInputData(reviewId, parentCommentId,
                            authorUsername, authorDisplayName, commentText);
            createComment(inputData.getReviewId(),
                    inputData.getParentCommentId(),
                    inputData.getAuthorUsername(),
                    inputData.getAuthorDisplayName(),
                    inputData.getCommentText());
            presenter.prepareSuccessView(true);
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Creates a new comment on a review.
     * @param reviewId the id of the review being commented on
     * @param parentCommentId the parent comment id, or null for a top-level
     * comment
     * @param authorUsername the comment author's username
     * @param authorDisplayName the comment author's display name
     * @param commentText the comment text
     * @return the created comment
     */
    private Comment createComment(final String reviewId,
                                 final String parentCommentId,
                                 final String authorUsername,
                                 final String authorDisplayName,
                                 final String commentText) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedParentCommentId = trimToNull(parentCommentId);
        final String trimmedAuthorUsername = trimToEmpty(authorUsername);
        final String trimmedAuthorDisplayName = trimToEmpty(authorDisplayName);
        final String trimmedCommentText = trimToEmpty(commentText);
        validateCommentData(trimmedReviewId, trimmedAuthorUsername,
                trimmedAuthorDisplayName, trimmedCommentText);

        final Comment comment = new Comment(UUID.randomUUID().toString(),
                trimmedReviewId, trimmedParentCommentId,
                trimmedAuthorUsername, trimmedAuthorDisplayName,
                trimmedCommentText, UserContent.getCurrentTorontoTime(),
                new HashSet<>());
        if (commentDataAccessObject != null) {
            commentDataAccessObject.saveComment(comment);
        }
        return comment;
    }

    /**
     * Validates submitted comment data.
     * @param reviewId the review id to validate
     * @param authorUsername the author's username to validate
     * @param authorDisplayName the author's display name to validate
     * @param commentText the comment text to validate
     * @throws IllegalArgumentException if any comment data is invalid
     * @throws IllegalStateException if the comment DAO is not configured
     */
    private void validateCommentData(final String reviewId,
                                     final String authorUsername,
                                     final String authorDisplayName,
                                     final String commentText) {
        if (isBlank(reviewId)) {
            throw new IllegalArgumentException("Review id cannot be empty.");
        } else if (isBlank(authorUsername)) {
            throw new IllegalArgumentException(
                    "Author username cannot be empty.");
        } else if (isBlank(authorDisplayName)) {
            throw new IllegalArgumentException(
                    "Author display name cannot be empty.");
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
                    "Create comment presenter has not been configured.");
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

    /**
     * Trims a text value, or returns null if it is blank.
     * @param value the value to trim
     * @return the trimmed value, or null
     */
    private String trimToNull(final String value) {
        final String trimmedValue;
        if (isBlank(value)) {
            trimmedValue = null;
        } else {
            trimmedValue = value.trim();
        }
        return trimmedValue;
    }
}
