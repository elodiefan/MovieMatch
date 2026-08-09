package use_case.review.edit_review;

import java.time.ZonedDateTime;
import java.util.Optional;

import entity.AbstractUserContent;
import entity.Review;

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

    /**
     * The review data access object.
     */
    private final EditReviewDataAccessInterface reviewDataAccessObject;
    /**
     * The presenter.
     */
    private final EditReviewOutputBoundary presenter;

    /**
     * Creates an edit review interactor without persistence.
     */
    public EditReviewInteractor() {
        this(null, null);
    }

    /**
     * Creates an edit review interactor with persistence.
     * @param inputReviewDataAccessObject the DAO used to edit reviews
     */
    public EditReviewInteractor(
            final EditReviewDataAccessInterface inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputReviewDataAccessObject the inputReviewDataAccessObject
     * @param inputPresenter the inputPresenter
     */
    public EditReviewInteractor(
            final EditReviewDataAccessInterface inputReviewDataAccessObject,
            final EditReviewOutputBoundary inputPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final EditReviewInputData inputData) {
        try {
            validatePresenter();
            final Review review = editReview(inputData.getReviewId(),
                    inputData.getUsername(), inputData.getRating(),
                    inputData.getReviewText());
            if (review == null) {
                presenter.prepareFailView("Review could not be edited.");
            }
            else {
                presenter.prepareSuccessView(toOutputData(review));
            }
        }
        catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Edits one persisted review written by the given user.
     * @param reviewId the id of the review to edit
     * @param username the username of the user editing the review
     * @param newRating the updated rating percentage
     * @param newReviewText the updated review text
     * @return the edited review, or null if it was not edited
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
        }
        else {
            editedReview = null;
        }
        return editedReview;
    }

    /**
     * Edits an existing review.
     * @param review the review to edit
     * @param newRating the updated rating percentage
     * @param newReviewText the updated review text
     * @return the edited review
     */
    private Review editReview(final Review review, final double newRating,
                             final String newReviewText) {
        validateEditReviewData(review, newRating);

        review.edit(newRating, trimToEmpty(newReviewText),
                AbstractUserContent.getCurrentTorontoTime());
        return review;
    }

    /**
     * Validates the data needed to edit a review.
     * @param review the review to validate
     * @param rating the rating to validate
     * @throws IllegalArgumentException if the review or rating is invalid
     */
    private void validateEditReviewData(final Review review,
                                        final double rating) {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null.");
        }
        else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException(
                    "Rating must be between 0 and 100.");
        }
    }

    /**
     * Validates the data needed to edit a persisted review.
     * @param reviewId the review id to validate
     * @param username the username to validate
     * @param rating the rating to validate
     * @throws IllegalArgumentException if the input data is invalid
     * @throws IllegalStateException if the review DAO is not configured
     */
    private void validateEditReviewData(final String reviewId,
                                        final String username,
                                        final double rating) {
        if (isBlank(reviewId)) {
            throw new IllegalArgumentException("Review id cannot be empty.");
        }
        else if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException(
                    "Rating must be between 0 and 100.");
        }
        else if (reviewDataAccessObject == null) {
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
     * @param review the review to edit
     * @param newRating the updated rating percentage
     * @param newReviewText the updated review text
     * @return the edited review
     */
    private Review editPersistedReview(final Review review,
                                       final double newRating,
                                       final String newReviewText) {
        final ZonedDateTime updatedAt = AbstractUserContent.getCurrentTorontoTime();
        reviewDataAccessObject.editReview(review.getReviewId(), newRating,
                newReviewText, updatedAt);
        review.edit(newRating, newReviewText, updatedAt);
        return review;
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

    private EditReviewOutputData toOutputData(final Review review) {
        return new EditReviewOutputData(review.getReviewId(),
                review.getAuthorUsername(), review.getAuthorDisplayName(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount(), review.getSource());
    }
}
