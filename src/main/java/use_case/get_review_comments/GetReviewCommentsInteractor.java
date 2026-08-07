package use_case.get_review_comments;

import java.util.Comparator;
import java.util.List;

import entity.Comment;
import use_case.get_review_comments.CommentSummaryMapper;

/**
 * Interactor for loading comments on a review.
 */
public final class GetReviewCommentsInteractor
        implements GetReviewCommentsInputBoundary {
    /** The comment data access object. */
    private final GetReviewCommentsDataAccessInterface commentDataAccessObject;
    /** The presenter. */
    private final GetReviewCommentsOutputBoundary presenter;

    /**
     * Creates a comments interactor without persistence.
     */
    public GetReviewCommentsInteractor() {
        this(null, null);
    }

    /**
     * Creates a comments interactor with persistence.
     */
    public GetReviewCommentsInteractor(
            final GetReviewCommentsDataAccessInterface
                    inputCommentDataAccessObject) {
        this(inputCommentDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     */
    public GetReviewCommentsInteractor(
            final GetReviewCommentsDataAccessInterface
                    inputCommentDataAccessObject,
            final GetReviewCommentsOutputBoundary inputPresenter) {
        this.commentDataAccessObject = inputCommentDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final String reviewId) {
        try {
            validatePresenter();
            final GetReviewCommentsInputData inputData =
                    new GetReviewCommentsInputData(reviewId);
            final List<Comment> comments = getReviewComments(
                    inputData.getReviewId());
            presenter.prepareSuccessView(
                    inputData.getReviewId(),
                    CommentSummaryMapper.toSummaries(comments));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Returns persisted comments on one review, ordered from oldest to newest.
     */
    private List<Comment> getReviewComments(final String reviewId) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        validateReviewId(trimmedReviewId);

        final List<Comment> matchingComments =
                commentDataAccessObject.getCommentsByReviewId(trimmedReviewId);
        matchingComments.sort(Comparator.comparing(Comment::getCreatedAt));
        return matchingComments;
    }

    /**
     * Validates the review id needed to load persisted comments.
     */
    private void validateReviewId(final String reviewId) {
        if (isBlank(reviewId)) {
            throw new IllegalArgumentException("Review id cannot be empty.");
        } else if (commentDataAccessObject == null) {
            throw new IllegalStateException(
                    "Comment data access object has not been configured.");
        }
    }

    private void validatePresenter() {
        if (presenter == null) {
            throw new IllegalStateException(
                    "Review comments presenter has not been configured.");
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
