package use_case.comment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Comment;

/**
 * Interactor for loading comments on a review.
 */
public class GetReviewCommentsInteractor {

    /**
     * Returns the comments on one review, ordered from oldest to newest.
     * @param reviewId the review id to load comments for
     * @param comments the comments to search through
     * @return the comments that belong to the review
     */
    public List<Comment> getReviewComments(final String reviewId,
                                           final List<Comment> comments) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        validateGetReviewCommentsData(trimmedReviewId, comments);

        final List<Comment> matchingComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (isMatchingReviewComment(comment, trimmedReviewId)) {
                matchingComments.add(comment);
            }
        }

        matchingComments.sort(Comparator.comparing(Comment::getCreatedAt));
        return matchingComments;
    }

    /**
     * Checks whether a comment belongs to the requested review.
     * @param comment the comment to check
     * @param reviewId the review id to match
     * @return true if the comment belongs to the review
     */
    private boolean isMatchingReviewComment(final Comment comment,
                                            final String reviewId) {
        final boolean matchingComment;
        if (comment == null) {
            matchingComment = false;
        } else {
            matchingComment = comment.getReviewId().equals(reviewId);
        }
        return matchingComment;
    }

    /**
     * Validates the data needed to load review comments.
     * @param reviewId the review id to validate
     * @param comments the comment list to validate
     */
    private void validateGetReviewCommentsData(final String reviewId,
                                               final List<Comment> comments) {
        if (isBlank(reviewId)) {
            throw new IllegalArgumentException("Review id cannot be empty.");
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
