package use_case.comment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import entity.Comment;
import entity.Review;

/**
 * Interactor for loading comments written by one user.
 */
public class GetUserCommentsInteractor {
    private final GetUserCommentsDataAccessInterface commentDataAccessObject;
    private final GetUserCommentsReviewDataAccessInterface reviewDataAccessObject;

    /**
     * Creates a user comments interactor.
     * @param commentDataAccessObject the DAO used to load comments
     * @param reviewDataAccessObject the DAO used to load related reviews
     */
    public GetUserCommentsInteractor(
            final GetUserCommentsDataAccessInterface commentDataAccessObject,
            final GetUserCommentsReviewDataAccessInterface reviewDataAccessObject) {
        this.commentDataAccessObject = commentDataAccessObject;
        this.reviewDataAccessObject = reviewDataAccessObject;
    }

    /**
     * Returns comments written by one user, ordered newest to oldest.
     * @param username the username of the comment author
     * @return the user's matching comments
     */
    public List<UserCommentSummaryData> getUserComments(
            final String username) {
        final String trimmedUsername = trimToEmpty(username);
        validateUsername(trimmedUsername);

        final List<Comment> comments =
                commentDataAccessObject.getCommentsByUsername(trimmedUsername);
        comments.sort(Comparator.comparing(Comment::getCreatedAt).reversed());

        final List<UserCommentSummaryData> commentSummaries =
                new ArrayList<>();
        for (Comment comment : comments) {
            commentSummaries.add(createCommentSummary(comment));
        }
        return commentSummaries;
    }

    /**
     * Creates summary data for one comment.
     * @param comment the comment to summarize
     * @return the comment summary
     */
    private UserCommentSummaryData createCommentSummary(final Comment comment) {
        final Optional<Review> review = reviewDataAccessObject.getReviewById(
                comment.getReviewId());
        final String mediaTitle;
        final String reviewText;
        if (review.isPresent()) {
            mediaTitle = review.get().getMediaTitle();
            reviewText = review.get().getReviewText();
        } else {
            mediaTitle = "";
            reviewText = "";
        }

        return new UserCommentSummaryData(comment.getCommentId(),
                comment.getReviewId(), mediaTitle, reviewText,
                comment.getCommentText(), comment.getCreatedAt(),
                comment.getLikeCount());
    }

    /**
     * Validates the username needed to load user comments.
     * @param username the username to validate
     */
    private void validateUsername(final String username) {
        if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
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
