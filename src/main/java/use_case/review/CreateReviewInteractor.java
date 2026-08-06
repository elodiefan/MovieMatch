package use_case.review;

import java.util.HashSet;
import java.util.UUID;

import entity.Review;
import entity.UserContent;

/**
 * Interactor for creating a review.
 */
public class CreateReviewInteractor {
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

    private final CreateReviewDataAccessInterface reviewDataAccessObject;

    /**
     * Creates a review interactor without persistence.
     */
    public CreateReviewInteractor() {
        this(null);
    }

    /**
     * Creates a review interactor with persistence.
     * @param reviewDataAccessObject the DAO used to save reviews
     */
    public CreateReviewInteractor(
            final CreateReviewDataAccessInterface reviewDataAccessObject) {
        this.reviewDataAccessObject = reviewDataAccessObject;
    }

    /**
     * Creates a new MovieMatch review.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @param mediaTitle the reviewed media's title
     * @param authorUsername the review author's username
     * @param authorDisplayName the review author's display name
     * @param rating the rating percentage
     * @param reviewText the written review text
     * @return the created review
     */
    public Review createReview(final int mediaId, final String mediaType,
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

    /**
     * Validates submitted review data.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @param mediaTitle the reviewed media's title
     * @param authorUsername the review author's username
     * @param authorDisplayName the review author's display name
     * @param rating the submitted rating
     */
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
