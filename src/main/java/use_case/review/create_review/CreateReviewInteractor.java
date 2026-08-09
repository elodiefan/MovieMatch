package use_case.review.create_review;

import java.util.HashSet;
import java.util.UUID;

import entity.Review;
import entity.UserContent;

/**
 * Interactor for creating a review.
 */
public final class CreateReviewInteractor implements CreateReviewInputBoundary {
    /**
     * Smallest valid rating percentage.
     */
    private static final double MIN_RATING = 0.0;

    /**
     * Largest valid rating percentage.
     */
    private static final double MAX_RATING = 100.0;

    /**
     * Source label for reviews created inside MovieMatch.
     */
    private static final String MOVIEMATCH_SOURCE = "moviematch";

    /**
     * The review data access object.
     */
    private final CreateReviewDataAccessInterface reviewDataAccessObject;

    /**
     * The user data access object.
     */
    private final CreateReviewUserDataAccessInterface userDataAccessObject;

    /**
     * The presenter.
     */
    private final CreateReviewOutputBoundary presenter;

    /**
     * Creates a review interactor without persistence.
     */
    public CreateReviewInteractor() {
        this(null, null, null);
    }

    /**
     * Creates a review interactor with persistence.
     * @param inputReviewDataAccessObject the DAO used to save reviews
     */
    public CreateReviewInteractor(
            final CreateReviewDataAccessInterface inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null, null);
    }

    /**
     * Creates a review interactor with persistence and presentation.
     * @param inputReviewDataAccessObject the DAO used to save reviews
     * @param inputPresenter the output boundary
     */
    public CreateReviewInteractor(
            final CreateReviewDataAccessInterface inputReviewDataAccessObject,
            final CreateReviewOutputBoundary inputPresenter) {
        this(inputReviewDataAccessObject, null, inputPresenter);
    }

    /**
     * Creates a review interactor with persistence, user lookup, and presentation.
     * @param inputReviewDataAccessObject the DAO used to save reviews
     * @param inputUserDataAccessObject the DAO used to check watch history
     * @param inputPresenter the output boundary
     */
    public CreateReviewInteractor(
            final CreateReviewDataAccessInterface inputReviewDataAccessObject,
            final CreateReviewUserDataAccessInterface inputUserDataAccessObject,
            final CreateReviewOutputBoundary inputPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.userDataAccessObject = inputUserDataAccessObject;
        this.presenter = inputPresenter;
    }

    /**
     * Executes the use case.
     * @param mediaId the media id
     * @param mediaType the media type
     * @param mediaTitle the media title
     * @param releaseYear the release year
     * @param posterPath the poster path
     * @param authorUsername the author's username
     * @param authorDisplayName the author's display name
     * @param rating the review rating
     * @param reviewText the review text
     */
    @Override
    public void execute(final int mediaId, final String mediaType,
                        final String mediaTitle, final int releaseYear,
                        final String posterPath, final String authorUsername,
                        final String authorDisplayName, final double rating,
                        final String reviewText) {
        try {
            validatePresenter();
            final CreateReviewInputData inputData =
                    new CreateReviewInputData(mediaId, mediaType, mediaTitle,
                            releaseYear, posterPath, authorUsername,
                            authorDisplayName, rating, reviewText);
            final Review review = createReview(inputData.getMediaId(),
                    inputData.getMediaType(), inputData.getMediaTitle(),
                    inputData.getReleaseYear(), inputData.getPosterPath(),
                    inputData.getAuthorUsername(),
                    inputData.getAuthorDisplayName(), inputData.getRating(),
                    inputData.getReviewText());
            presenter.prepareSuccessView(toOutputData(review));
        }
        catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    @Override
    public boolean canCreateReview(final int mediaId, final String mediaType,
                                   final String authorUsername) {
        final String error = getReviewPermissionError(mediaId,
                trimToEmpty(mediaType), trimToEmpty(authorUsername));
        final boolean canCreate = error == null;
        if (!canCreate) {
            if (presenter != null) {
                presenter.prepareFailView(error);
            }
        }
        return canCreate;
    }

    /**
     * Creates a new MovieMatch review.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @param mediaTitle the reviewed media's title
     * @param releaseYear the reviewed media's release year
     * @param posterPath the reviewed media's poster path
     * @param authorUsername the review author's username
     * @param authorDisplayName the review author's display name
     * @param rating the rating percentage
     * @param reviewText the written review text
     * @return the created review
     */
    private Review createReview(final int mediaId, final String mediaType,
                               final String mediaTitle,
                               final int releaseYear,
                               final String posterPath,
                               final String authorUsername,
                               final String authorDisplayName,
                               final double rating,
                               final String reviewText) {
        final String trimmedMediaType = trimToEmpty(mediaType);
        final String trimmedMediaTitle = trimToEmpty(mediaTitle);
        final String trimmedPosterPath = trimToEmpty(posterPath);
        final String trimmedAuthorUsername = trimToEmpty(authorUsername);
        final String trimmedAuthorDisplayName = trimToEmpty(authorDisplayName);
        final String trimmedReviewText = trimToEmpty(reviewText);
        validateReviewData(mediaId, trimmedMediaType, trimmedMediaTitle,
                trimmedAuthorUsername, trimmedAuthorDisplayName, rating);

        final String reviewId = UUID.randomUUID().toString();
        final Review review = new Review(reviewId, mediaId, trimmedMediaType,
                trimmedMediaTitle, releaseYear, trimmedPosterPath,
                trimmedAuthorUsername,
                trimmedAuthorDisplayName, rating, trimmedReviewText,
                UserContent.getCurrentTorontoTime(),
                UserContent.getCurrentTorontoTime(), MOVIEMATCH_SOURCE,
                new HashSet<>());
        if (reviewDataAccessObject != null) {
            reviewDataAccessObject.saveReview(review);
        }
        return review;
    }

    /**
     * Validates submitted review data.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @param mediaTitle the reviewed media's title
     * @param authorUsername the review author's username
     * @param authorDisplayName the review author's display name
     * @param rating the submitted rating
     * @throws IllegalArgumentException if any review data is invalid
     * @throws IllegalStateException if the review DAO is not configured
     */
    private void validateReviewData(final int mediaId, final String mediaType,
                                    final String mediaTitle,
                                    final String authorUsername,
                                    final String authorDisplayName,
                                    final double rating) {
        if (mediaId < 0) {
            throw new IllegalArgumentException("Media id cannot be negative.");
        }
        else if (isBlank(mediaType)) {
            throw new IllegalArgumentException("Media type cannot be empty.");
        }
        else if (isBlank(mediaTitle)) {
            throw new IllegalArgumentException("Media title cannot be empty.");
        }
        else if (isBlank(authorUsername)) {
            throw new IllegalArgumentException(
                    "Author username cannot be empty.");
        }
        else if (isBlank(authorDisplayName)) {
            throw new IllegalArgumentException(
                    "Author display name cannot be empty.");
        }
        else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException(
                    "Rating must be between 0 and 100.");
        }
        else if (reviewDataAccessObject == null) {
            throw new IllegalStateException(
                    "Review data access object has not been configured.");
        }
        else {
            validateReviewPermission(mediaId, mediaType, authorUsername);
        }
    }

    /**
     * Validates whether the author is allowed to review the selected media.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @param authorUsername the review author's username
     * @throws IllegalArgumentException the exception when the argument is invalid
     */
    private void validateReviewPermission(final int mediaId,
                                          final String mediaType,
                                          final String authorUsername) throws IllegalArgumentException {
        final String error = getReviewPermissionError(mediaId, mediaType,
                authorUsername);
        if (error != null) {
            throw new IllegalArgumentException(
                    error);
        }
    }

    /**
     * Gets a review permission error, or null when the user may review.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @param authorUsername the review author's username
     * @return the permission error, or null when the user may review
     */
    private String getReviewPermissionError(final int mediaId,
                                            final String mediaType,
                                            final String authorUsername) {
        final String error;
        if (userDataAccessObject == null) {
            error = "User data access object has not been configured.";
        }
        else if (!userDataAccessObject.hasWatchedMedia(authorUsername,
                mediaId, mediaType)) {
            error = "Please add this media to your watch history before writing a review.";
        }
        else {
            error = null;
        }
        return error;
    }

    private void validatePresenter() {
        if (presenter == null) {
            throw new IllegalStateException(
                    "Create review presenter has not been configured.");
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

    private CreateReviewOutputData toOutputData(final Review review) {
        return new CreateReviewOutputData(review.getReviewId(),
                review.getAuthorUsername(), review.getAuthorDisplayName(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount(), review.getSource());
    }
}
