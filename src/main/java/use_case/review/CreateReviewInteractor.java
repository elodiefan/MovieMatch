package use_case.review;

import java.util.HashSet;
import java.util.UUID;

import entity.Review;
import entity.UserContent;

/**
 * Interactor for creating a review.
 */
public class CreateReviewInteractor {
    private static final double MIN_RATING = 0.0;
    private static final double MAX_RATING = 100.0;
    private static final String MOVIEMATCH_SOURCE = "moviematch";

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
    public Review createReview(int mediaId, String mediaType, String mediaTitle,
                               String authorUsername, String authorDisplayName,
                               double rating, String reviewText) {
        final String trimmedMediaType = trimToEmpty(mediaType);
        final String trimmedMediaTitle = trimToEmpty(mediaTitle);
        final String trimmedAuthorUsername = trimToEmpty(authorUsername);
        final String trimmedAuthorDisplayName = trimToEmpty(authorDisplayName);
        final String trimmedReviewText = trimToEmpty(reviewText);
        validateReviewData(mediaId, trimmedMediaType, trimmedMediaTitle,
                trimmedAuthorUsername, trimmedAuthorDisplayName, rating);

        return new Review(UUID.randomUUID().toString(), mediaId, trimmedMediaType,
                trimmedMediaTitle, trimmedAuthorUsername, trimmedAuthorDisplayName,
                rating, trimmedReviewText, UserContent.getCurrentTorontoTime(),
                UserContent.getCurrentTorontoTime(), MOVIEMATCH_SOURCE, new HashSet<>());
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
    private void validateReviewData(int mediaId, String mediaType, String mediaTitle,
                                    String authorUsername, String authorDisplayName,
                                    double rating) {
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
            throw new IllegalArgumentException("Author username cannot be empty.");
        }
        else if (isBlank(authorDisplayName)) {
            throw new IllegalArgumentException("Author display name cannot be empty.");
        }
        else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between 0 and 100.");
        }
    }

    /**
     * Checks whether a text value is empty or only whitespace.
     * @param value the value to check
     * @return true if the value is blank
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Trims a text value, or returns an empty string if it is null.
     * @param value the value to trim
     * @return the trimmed value
     */
    private String trimToEmpty(String value) {
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
