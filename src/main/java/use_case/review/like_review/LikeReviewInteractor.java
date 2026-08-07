package use_case.review.like_review;

/**
 * Interactor for liking a review.
 */
public final class LikeReviewInteractor implements LikeReviewInputBoundary {
    /** The review data access object. */
    private final LikeReviewDataAccessInterface reviewDataAccessObject;
    /** The presenter. */
    private final LikeReviewOutputBoundary presenter;

    /**
     * Creates a like review interactor without persistence.
     */
    public LikeReviewInteractor() {
        this(null, null);
    }

    /**
     * Creates a like review interactor with persistence.
     */
    public LikeReviewInteractor(
            final LikeReviewDataAccessInterface inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     */
    public LikeReviewInteractor(
            final LikeReviewDataAccessInterface inputReviewDataAccessObject,
            final LikeReviewOutputBoundary inputPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final String reviewId, final String username) {
        try {
            validatePresenter();
            final LikeReviewInputData inputData =
                    new LikeReviewInputData(reviewId, username);
            final boolean liked = likeReview(inputData.getReviewId(),
                    inputData.getUsername());
            presenter.prepareSuccessView(liked);
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Adds a user's like to a persisted review.
     */
    private boolean likeReview(final String reviewId, final String username) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedUsername = trimToEmpty(username);
        validateLikeReviewData(trimmedReviewId, trimmedUsername);
        return reviewDataAccessObject.likeReview(trimmedReviewId,
                trimmedUsername);
    }

    /**
     * Validates the data needed to like a persisted review.
     */
    private void validateLikeReviewData(final String reviewId,
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
                    "Like review presenter has not been configured.");
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
