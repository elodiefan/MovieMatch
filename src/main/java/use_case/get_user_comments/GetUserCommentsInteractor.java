package use_case.get_user_comments;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import entity.Comment;
import entity.Review;

/**
 * Interactor for loading comments written by one user.
 */
public final class GetUserCommentsInteractor
        implements GetUserCommentsInputBoundary {
    /** The comment data access object. */
    private final GetUserCommentsDataAccessInterface commentDataAccessObject;
    /** The review data access object. */
    private final GetUserCommentsReviewDataAccessInterface
            reviewDataAccessObject;
    /** The user comments presenter. */
    private final GetUserCommentsOutputBoundary userCommentsPresenter;

    /**
     * Creates a user comments interactor.
     */
    public GetUserCommentsInteractor(
            final GetUserCommentsDataAccessInterface
                    inputCommentDataAccessObject,
            final GetUserCommentsReviewDataAccessInterface
                    inputReviewDataAccessObject) {
        this(inputCommentDataAccessObject, inputReviewDataAccessObject, null);
    }

    /**
     * Creates a user comments interactor.
     */
    public GetUserCommentsInteractor(
            final GetUserCommentsDataAccessInterface
                    inputCommentDataAccessObject,
            final GetUserCommentsReviewDataAccessInterface
                    inputReviewDataAccessObject,
            final GetUserCommentsOutputBoundary inputUserCommentsPresenter) {
        this.commentDataAccessObject = inputCommentDataAccessObject;
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.userCommentsPresenter = inputUserCommentsPresenter;
    }

    /**
     * Executes the use case and sends output through the output boundary.
     */
    @Override
    public void execute(final String username) {
        try {
            validateOutputBoundary();
            final GetUserCommentsInputData inputData =
                    new GetUserCommentsInputData(username);
            final List<GetUserCommentsOutputData.UserCommentData> comments =
                    getUserComments(inputData.getUsername());
            userCommentsPresenter.prepareUserCommentsSuccessView(
                    new GetUserCommentsOutputData(comments));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (userCommentsPresenter != null) {
                userCommentsPresenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Returns comments written by one user, ordered newest to oldest.
     */
    private List<GetUserCommentsOutputData.UserCommentData> getUserComments(
            final String username) {
        final String trimmedUsername = trimToEmpty(username);
        validateUsername(trimmedUsername);

        final List<Comment> comments =
                commentDataAccessObject.getCommentsByUsername(trimmedUsername);
        comments.sort(Comparator.comparing(Comment::getCreatedAt).reversed());

        final List<GetUserCommentsOutputData.UserCommentData> commentSummaries =
                new ArrayList<>();
        for (Comment comment : comments) {
            commentSummaries.add(createCommentSummary(comment));
        }
        return commentSummaries;
    }

    /**
     * Creates summary data for one comment.
     */
    private GetUserCommentsOutputData.UserCommentData createCommentSummary(
            final Comment comment) {
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

        return new GetUserCommentsOutputData.UserCommentData(
                comment.getCommentId(),
                comment.getReviewId(), mediaTitle, reviewText,
                comment.getCommentText(), comment.getCreatedAt(),
                comment.getLikeCount());
    }

    /**
     * Validates the username needed to load user comments.
     */
    private void validateUsername(final String username) {
        if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
    }

    /**
     * Validates that the output boundary has been configured.
     */
    private void validateOutputBoundary() {
        if (userCommentsPresenter == null) {
            throw new IllegalStateException(
                    "User comments presenter has not been configured.");
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
