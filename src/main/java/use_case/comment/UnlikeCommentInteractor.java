package use_case.comment;

import java.util.List;

import entity.Comment;

/**
 * Interactor for unliking a comment.
 */
public class UnlikeCommentInteractor {

    /**
     * Removes a user's like from a comment.
     * @param commentId the id of the comment to unlike
     * @param username the username of the user unliking the comment
     * @param comments the comments to search through
     * @return true if the comment was found and unliked
     */
    public boolean unlikeComment(final String commentId, final String username,
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
