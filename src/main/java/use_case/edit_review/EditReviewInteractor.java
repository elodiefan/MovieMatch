package use_case.edit_review;

import java.time.ZonedDateTime;
import java.util.Optional;

import entity.Review;
import entity.UserContent;

/**
 * Interactor for editing a review.
 */
public final class EditReviewInteractor implements EditReviewInputBoundary {
    /**
     * Smallest valid rating percentage.
     */
    private static final double MIN_RATING = 0.0;

    /**
     * Largest valid rating percentage.
     */
    private static final double MAX_RATING = 100.0;

    /** The review data access object. */
    private final EditReviewDataAccessInterface reviewDataAccessObject;
    /** The presenter. */
    private final EditReviewOutputBoundary presenter;

    /**
     * Creates an edit review interactor without persistence.
     */
    public EditReviewInteractor() {
        this(null, null);
    }

    /**
     * Creates an edit review interactor with persistence.
     */
    public EditReviewInteractor(
            final EditReviewDataAccessInterface inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     */
    public EditReviewInteractor(
            final EditReviewDataAccessInterface inputReviewDataAccessObject,
            final EditReviewOutputBoundary inputPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final String reviewId, final String username,
                        final double rating, final String reviewText) {
        try {
            validatePresenter();
            final EditReviewInputData inputData =
                    new EditReviewInputData(reviewId, username, rating,
                            reviewText);
            final Review review = editReview(inputData.getReviewId(),
                    inputData.getUsername(), inputData.getRating(),
                    inputData.getReviewText());
            if (review == null) {
                presenter.prepareFailView("Review could not be edited.");
            } else {
                presenter.prepareSuccessView(toOutputData(review));
            }
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Edits one persisted review written by the given user.
     */
    private Review editReview(final String reviewId, final String username,
                             final double newRating,
                             final String newReviewText) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedUsername = trimToEmpty(username);
        final String trimmedReviewText = trimToEmpty(newReviewText);
        validateEditReviewData(trimmedReviewId, trimmedUsername, newRating);

        final Optional<Review> review =
                reviewDataAccessObject.getReviewById(trimmedReviewId);
        final Review editedReview;
        if (review.isPresent()
                && review.get().getAuthorUsername().equals(trimmedUsername)) {
            editedReview = editPersistedReview(review.get(), newRating,
                    trimmedReviewText);
        } else {
            editedReview = null;
        }
        return editedReview;
    }

    /**
     * Edits an existing review.
     */
    private Review editReview(final Review review, final double newRating,
                             final String newReviewText) {
        validateEditReviewData(review, newRating);

        review.edit(newRating, trimToEmpty(newReviewText),
                UserContent.getCurrentTorontoTime());
        return review;
    }

    /**
     * Validates the data needed to edit a review.
     */
    private void validateEditReviewData(final Review review,
                                        final double rating) {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null.");
        } else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException(
                    "Rating must be between 0 and 100.");
        }
    }

    /**
     * Validates the data needed to edit a persisted review.
     */
    private void validateEditReviewData(final String reviewId,
                                        final String username,
                                        final double rating) {
        if (isBlank(reviewId)) {
            throw new IllegalArgumentException("Review id cannot be empty.");
        } else if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        } else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException(
                    "Rating must be between 0 and 100.");
        } else if (reviewDataAccessObject == null) {
            throw new IllegalStateException(
                    "Review data access object has not been configured.");
        }
    }

    private void validatePresenter() {
        if (presenter == null) {
            throw new IllegalStateException(
                    "Edit review presenter has not been configured.");
        }
    }

    /**
     * Saves an edited review through the DAO and updates the entity copy.
     */
    private Review editPersistedReview(final Review review,
                                       final double newRating,
                                       final String newReviewText) {
        final ZonedDateTime updatedAt = UserContent.getCurrentTorontoTime();
        reviewDataAccessObject.editReview(review.getReviewId(), newRating,
                newReviewText, updatedAt);
        review.edit(newRating, newReviewText, updatedAt);
        return review;
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

    private EditReviewOutputData toOutputData(final Review review) {
        return new EditReviewOutputData(review.getReviewId(),
                review.getAuthorUsername(), review.getAuthorDisplayName(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount(), review.getSource());
    }
}
