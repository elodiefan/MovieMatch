package use_case.get_review_comments;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

import entity.Comment;

/**
 * Interactor for loading comments on a review.
 */
public final class GetReviewCommentsInteractor
        implements GetReviewCommentsInputBoundary {
    /**
     * The comment data access object.
     */
    private final GetReviewCommentsDataAccessInterface commentDataAccessObject;
    /**
     * The presenter.
     */
    private final GetReviewCommentsOutputBoundary presenter;

    /**
     * Creates a comments interactor without persistence.
     */
    public GetReviewCommentsInteractor() {
        this(null, null);
    }

    /**
     * Creates a comments interactor with persistence.
     * @param inputCommentDataAccessObject the DAO used to load comments
     */
    public GetReviewCommentsInteractor(
            final GetReviewCommentsDataAccessInterface
                    inputCommentDataAccessObject) {
        this(inputCommentDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputCommentDataAccessObject the inputCommentDataAccessObject
     * @param inputPresenter the inputPresenter
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
            presenter.prepareSuccessView(toOutputData(inputData.getReviewId(),
                    comments));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Returns persisted comments on one review, ordered from oldest to newest.
     * @param reviewId the review id to load comments for
     * @return the comments that belong to the review
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
     * @param reviewId the review id to validate
     * @throws IllegalArgumentException if the review id is blank
     * @throws IllegalStateException if the comment DAO is not configured
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

    private GetReviewCommentsOutputData toOutputData(final String reviewId,
                                                     final List<Comment>
                                                             comments) {
        final List<GetReviewCommentsOutputData.ReviewCommentData>
                outputComments = new ArrayList<>();
        for (Comment comment : comments) {
            outputComments.add(new GetReviewCommentsOutputData
                    .ReviewCommentData(comment.getCommentId(),
                    comment.getReviewId(), comment.getParentCommentId(),
                    comment.getAuthorUsername(), comment.getAuthorDisplayName(),
                    comment.getCommentText(), comment.getCreatedAt(),
                    comment.getLikeCount()));
        }
        return new GetReviewCommentsOutputData(reviewId, outputComments);
    }
}
