package use_case.review.create_review;

import java.util.HashSet;
import java.util.UUID;

import entity.Review;
import entity.UserContent;

/** Interactor for creating a review. */
public final class CreateReviewInteractor implements CreateReviewInputBoundary {
    /** Smallest valid rating percentage. */
    private static final double MIN_RATING = 0.0;

    /** Largest valid rating percentage. */
    private static final double MAX_RATING = 100.0;

    /** Source label for reviews created inside MovieMatch. */
    private static final String MOVIEMATCH_SOURCE = "moviematch";

    /** The review data access object. */
    private final CreateReviewDataAccessInterface reviewDataAccessObject;
    /** The presenter. */
    private final CreateReviewOutputBoundary presenter;

    /** Creates a review interactor without persistence. */
    public CreateReviewInteractor() {
        this(null, null);
    }

    /** Creates a review interactor with persistence. */
    public CreateReviewInteractor(
            final CreateReviewDataAccessInterface inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /** Creates a review interactor with persistence and presentation. */
    public CreateReviewInteractor(
            final CreateReviewDataAccessInterface inputReviewDataAccessObject,
            final CreateReviewOutputBoundary inputPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.presenter = inputPresenter;
    }

    /** Executes the use case. */
    @Override
    public void execute(final CreateReviewInputData inputData) {
        try {
            validatePresenter();
            final Review review = createReview(inputData.getMediaId(),
                    inputData.getMediaType(), inputData.getMediaTitle(),
                    inputData.getAuthorUsername(),
                    inputData.getAuthorDisplayName(), inputData.getRating(),
                    inputData.getReviewText());
            presenter.prepareSuccessView(new CreateReviewOutputData(review));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /** Creates a new MovieMatch review. */
    private Review createReview(final int mediaId, final String mediaType,
                               final String mediaTitle,
                               final String authorUsername,
                               final String authorDisplayName,
                               final double rating,
                               final String reviewText) {
        final String trimmedMediaType = trimToEmpty(mediaType);
        final String trimmedMediaTitle = trimToEmpty(mediaTitle);
        final String trimmedAuthorUsername = trimToEmpty(authorUsername);
        final String trimmedAuthorDisplayName = trimToEmpty(authorDisplayName);
        final String trimmedReviewText = trimToEmpty(reviewText);
        validateReviewData(mediaId, trimmedMediaType, trimmedMediaTitle,
                trimmedAuthorUsername, trimmedAuthorDisplayName, rating);

        final String reviewId = UUID.randomUUID().toString();
        final Review review = new Review(reviewId, mediaId, trimmedMediaType,
                trimmedMediaTitle, trimmedAuthorUsername,
                trimmedAuthorDisplayName, rating, trimmedReviewText,
                UserContent.getCurrentTorontoTime(),
                UserContent.getCurrentTorontoTime(), MOVIEMATCH_SOURCE,
                new HashSet<>());
        if (reviewDataAccessObject != null) {
            reviewDataAccessObject.saveReview(review);
        }
        return review;
    }

    /** Validates submitted review data. */
    private void validateReviewData(final int mediaId, final String mediaType,
                                    final String mediaTitle,
                                    final String authorUsername,
                                    final String authorDisplayName,
                                    final double rating) {
        if (mediaId < 0) {
            throw new IllegalArgumentException("Media id cannot be negative.");
        } else if (isBlank(mediaType)) {
            throw new IllegalArgumentException("Media type cannot be empty.");
        } else if (isBlank(mediaTitle)) {
            throw new IllegalArgumentException("Media title cannot be empty.");
        } else if (isBlank(authorUsername)) {
            throw new IllegalArgumentException(
                    "Author username cannot be empty.");
        } else if (isBlank(authorDisplayName)) {
            throw new IllegalArgumentException(
                    "Author display name cannot be empty.");
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
                    "Create review presenter has not been configured.");
        }
    }

    /** Checks whether a text value is empty or only whitespace. */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

    /** Trims a text value, or returns an empty string if it is null. */
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
