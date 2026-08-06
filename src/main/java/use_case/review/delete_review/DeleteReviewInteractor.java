package use_case.review.delete_review;

import java.util.Optional;

import entity.Review;

/**
 * Interactor for deleting a review.
 */
public final class DeleteReviewInteractor implements DeleteReviewInputBoundary {
    /** The review data access object. */
    private final DeleteReviewDataAccessInterface reviewDataAccessObject;
    /** The presenter. */
    private final DeleteReviewOutputBoundary presenter;

    /**
     * Creates a delete review interactor without persistence.
     */
    public DeleteReviewInteractor() {
        this(null, null);
    }

    /**
     * Creates a delete review interactor with persistence.
     * @param inputReviewDataAccessObject the DAO used to delete reviews
     */
    public DeleteReviewInteractor(
            final DeleteReviewDataAccessInterface inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputReviewDataAccessObject the inputReviewDataAccessObject
     * @param inputPresenter the inputPresenter
     */
    public DeleteReviewInteractor(
            final DeleteReviewDataAccessInterface inputReviewDataAccessObject,
            final DeleteReviewOutputBoundary inputPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final DeleteReviewInputData inputData) {
        try {
            validatePresenter();
            final boolean deleted = deleteReview(inputData.getReviewId(),
                    inputData.getUsername());
            if (deleted) {
                presenter.prepareSuccessView(
                        new DeleteReviewOutputData(true));
            } else {
                presenter.prepareFailView("Review could not be deleted.");
            }
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Deletes one persisted review written by the given user.
     * @param reviewId the id of the review to delete
     * @param username the username of the user deleting the review
     * @return true if the review was deleted
     */
    private boolean deleteReview(final String reviewId,
                                final String username) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedUsername = trimToEmpty(username);
        validateDeleteReviewData(trimmedReviewId, trimmedUsername);

        final Optional<Review> review =
                reviewDataAccessObject.getReviewById(trimmedReviewId);
        final boolean deleted;
        if (review.isPresent()
                && canDeleteReview(review.get(), trimmedReviewId,
                trimmedUsername)) {
            deleted = reviewDataAccessObject.deleteReview(trimmedReviewId);
        } else {
            deleted = false;
        }
        return deleted;
    }

    /**
     * Validates data needed to delete a persisted review.
     * @param reviewId the review id to validate
     * @param username the username to validate
     */
    private void validateDeleteReviewData(final String reviewId,
                                          final String username) {
        if (isBlank(reviewId)) {
            throw new IllegalArgumentException("Review id cannot be empty.");
        } else if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        } else if (reviewDataAccessObject == null) {
            throw new IllegalStateException(
                    "Review data access object has not been configured.");
        }
    }

    private void validatePresenter() {
        if (presenter == null) {
            throw new IllegalStateException(
                    "Delete review presenter has not been configured.");
        }
    }

    /**
     * Checks whether the review can be deleted by the user.
     * @param review the review to check
     * @param reviewId the id of the review to delete
     * @param username the username of the user deleting the review
     * @return true if the review matches the id and author
     */
    private boolean canDeleteReview(final Review review, final String reviewId,
                                    final String username) {
        final boolean canDelete;
        if (review == null) {
            canDelete = false;
        } else {
            canDelete = review.getReviewId().equals(reviewId)
                    && review.getAuthorUsername().equals(username);
        }
        return canDelete;
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
