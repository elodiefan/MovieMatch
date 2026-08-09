package use_case.review.unlike_review;

/**
 * Interactor for unliking a review.
 */
public final class UnlikeReviewInteractor implements UnlikeReviewInputBoundary {
    /**
     * The review data access object.
     */
    private final UnlikeReviewDataAccessInterface reviewDataAccessObject;
    /**
     * The presenter.
     */
    private final UnlikeReviewOutputBoundary presenter;

    /**
     * Creates an unlike review interactor without persistence.
     */
    public UnlikeReviewInteractor() {
        this(null, null);
    }

    /**
     * Creates an unlike review interactor with persistence.
     * @param inputReviewDataAccessObject the DAO used to unlike reviews
     */
    public UnlikeReviewInteractor(
            final UnlikeReviewDataAccessInterface inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputReviewDataAccessObject the inputReviewDataAccessObject
     * @param inputPresenter the inputPresenter
     */
    public UnlikeReviewInteractor(
            final UnlikeReviewDataAccessInterface inputReviewDataAccessObject,
            final UnlikeReviewOutputBoundary inputPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final UnlikeReviewInputData inputData) {
        try {
            validatePresenter();
            final boolean unliked = unlikeReview(inputData.getReviewId(),
                    inputData.getUsername());
            presenter.prepareSuccessView(unliked);
        }
        catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Removes a user's like from a persisted review.
     * @param reviewId the id of the review to unlike
     * @param username the username of the user unliking the review
     * @return true if the review was found and unliked
     */
    private boolean unlikeReview(final String reviewId,
                                final String username) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedUsername = trimToEmpty(username);
        validateUnlikeReviewData(trimmedReviewId, trimmedUsername);
        return reviewDataAccessObject.unlikeReview(trimmedReviewId,
                trimmedUsername);
    }

    /**
     * Validates the data needed to unlike a persisted review.
     * @param reviewId the review id to validate
     * @param username the username to validate
     * @throws IllegalArgumentException if the review id or username is blank
     * @throws IllegalStateException if the review DAO is not configured
     */
    private void validateUnlikeReviewData(final String reviewId,
                                          final String username) {
        if (isBlank(reviewId)) {
            throw new IllegalArgumentException("Review id cannot be empty.");
        }
        else if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        else if (reviewDataAccessObject == null) {
            throw new IllegalStateException(
                    "Review data access object has not been configured.");
        }
    }

    private void validatePresenter() {
        if (presenter == null) {
            throw new IllegalStateException(
                    "Unlike review presenter has not been configured.");
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
        }
        else {
            trimmedValue = value.trim();
        }
        return trimmedValue;
    }
}
