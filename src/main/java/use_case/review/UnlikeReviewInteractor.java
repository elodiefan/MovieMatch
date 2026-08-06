package use_case.review;

import java.util.List;

import entity.Review;

/**
 * Interactor for unliking a review.
 */
public class UnlikeReviewInteractor implements UnlikeReviewInputBoundary {
    private final UnlikeReviewDataAccessInterface reviewDataAccessObject;
    private final UnlikeReviewOutputBoundary presenter;

    /**
     * Creates an unlike review interactor without persistence.
     */
    public UnlikeReviewInteractor() {
        this(null, null);
    }

    /**
     * Creates an unlike review interactor with persistence.
     * @param reviewDataAccessObject the DAO used to unlike reviews
     */
    public UnlikeReviewInteractor(
            final UnlikeReviewDataAccessInterface reviewDataAccessObject) {
        this(reviewDataAccessObject, null);
    }

    public UnlikeReviewInteractor(
            final UnlikeReviewDataAccessInterface reviewDataAccessObject,
            final UnlikeReviewOutputBoundary presenter) {
        this.reviewDataAccessObject = reviewDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(final UnlikeReviewInputData inputData) {
        try {
            validatePresenter();
            final boolean unliked = unlikeReview(inputData.getReviewId(),
                    inputData.getUsername());
            presenter.prepareSuccessView(new UnlikeReviewOutputData(unliked));
        } catch (IllegalArgumentException | IllegalStateException error) {
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
     * Removes a user's like from a review.
     * @param reviewId the id of the review to unlike
     * @param username the username of the user unliking the review
     * @param reviews the reviews to search through
     * @return true if the review was found and unliked
     */
    private boolean unlikeReview(final String reviewId, final String username,
                                final List<Review> reviews) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedUsername = trimToEmpty(username);
        validateUnlikeReviewData(trimmedReviewId, trimmedUsername, reviews);

        boolean unliked = false;
        for (Review review : reviews) {
            if (isMatchingReview(review, trimmedReviewId)) {
                review.unlike(trimmedUsername);
                unliked = true;
            }
        }

        return unliked;
    }

    /**
     * Validates the data needed to unlike a persisted review.
     * @param reviewId the review id to validate
     * @param username the username to validate
     */
    private void validateUnlikeReviewData(final String reviewId,
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
                    "Unlike review presenter has not been configured.");
        }
    }

    /**
     * Checks whether a review has the requested review id.
     * @param review the review to check
     * @param reviewId the review id to match
     * @return true if the review has the requested id
     */
    private boolean isMatchingReview(final Review review,
                                     final String reviewId) {
        final boolean matchingReview;
        if (review == null) {
            matchingReview = false;
        } else {
            matchingReview = review.getReviewId().equals(reviewId);
        }
        return matchingReview;
    }

    /**
     * Validates the data needed to unlike a review.
     * @param reviewId the review id to validate
     * @param username the username to validate
     * @param reviews the review list to validate
     */
    private void validateUnlikeReviewData(final String reviewId,
                                          final String username,
                                          final List<Review> reviews) {
        if (isBlank(reviewId)) {
            throw new IllegalArgumentException("Review id cannot be empty.");
        } else if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        } else if (reviews == null) {
            throw new IllegalArgumentException("Reviews cannot be null.");
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
