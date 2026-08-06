package use_case.comment;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import entity.Comment;

/**
 * Interactor for deleting a comment.
 */
public class DeleteCommentInteractor {
    private final DeleteCommentDataAccessInterface commentDataAccessObject;

    /**
     * Creates a delete comment interactor without persistence.
     */
    public DeleteCommentInteractor() {
        this(null);
    }

    /**
     * Creates a delete comment interactor with persistence.
     * @param commentDataAccessObject the DAO used to delete comments
     */
    public DeleteCommentInteractor(
            final DeleteCommentDataAccessInterface commentDataAccessObject) {
        this.commentDataAccessObject = commentDataAccessObject;
    }

    /**
     * Deletes one persisted comment written by the given user.
     * @param commentId the id of the comment to delete
     * @param username the username of the user deleting the comment
     * @return true if the comment was deleted
     */
    public boolean deleteComment(final String commentId,
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
     * Deletes one comment written by the given user.
     * @param commentId the id of the comment to delete
     * @param username the username of the user deleting the comment
     * @param comments the comments to search through
     * @return true if the comment was deleted
     */
    public boolean deleteComment(final String commentId, final String username,
                                 final List<Comment> comments) {
        final String trimmedCommentId = trimToEmpty(commentId);
        final String trimmedUsername = trimToEmpty(username);
        validateDeleteCommentData(trimmedCommentId, trimmedUsername, comments);

        boolean deleted = false;
        final Iterator<Comment> commentIterator = comments.iterator();
        while (commentIterator.hasNext() && !deleted) {
            final Comment comment = commentIterator.next();
            if (canDeleteComment(comment, trimmedCommentId, trimmedUsername)) {
                commentIterator.remove();
                deleted = true;
            }
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
     * Validates the data needed to delete a comment.
     * @param commentId the comment id to validate
     * @param username the username to validate
     * @param comments the comment list to validate
     */
    private void validateDeleteCommentData(final String commentId,
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
