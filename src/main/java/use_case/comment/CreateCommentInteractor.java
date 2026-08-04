package use_case.comment;

import java.util.HashSet;
import java.util.UUID;

import entity.Comment;
import entity.UserContent;

/**
 * Interactor for creating a comment.
 */
public class CreateCommentInteractor {

    /**
     * Creates a new comment on a review.
     * @param reviewId the id of the review being commented on
     * @param parentCommentId the parent comment id, or null for a top-level
     *                         comment
     * @param authorUsername the comment author's username
     * @param authorDisplayName the comment author's display name
     * @param commentText the comment text
     * @return the created comment
     */
    public Comment createComment(final String reviewId,
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

        return new Comment(UUID.randomUUID().toString(), trimmedReviewId,
                trimmedParentCommentId, trimmedAuthorUsername,
                trimmedAuthorDisplayName, trimmedCommentText,
                UserContent.getCurrentTorontoTime(), new HashSet<>());
    }

    /**
     * Validates submitted comment data.
     * @param reviewId the review id to validate
     * @param authorUsername the author's username to validate
     * @param authorDisplayName the author's display name to validate
     * @param commentText the comment text to validate
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
