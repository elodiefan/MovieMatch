package use_case.review;

import entity.Review;
import entity.UserContent;

/**
 * Interactor for editing a review.
 */
public class EditReviewInteractor {
    private static final double MIN_RATING = 0.0;
    private static final double MAX_RATING = 100.0;

    /**
     * Edits an existing review.
     * @param review the review to edit
     * @param newRating the updated rating percentage
     * @param newReviewText the updated review text
     * @return the edited review
     */
    public Review editReview(Review review, double newRating, String newReviewText) {
        validateEditReviewData(review, newRating);

        review.edit(newRating, trimToEmpty(newReviewText), UserContent.getCurrentTorontoTime());
        return review;
    }

    /**
     * Validates the data needed to edit a review.
     * @param review the review to validate
     * @param rating the rating to validate
     */
    private void validateEditReviewData(Review review, double rating) {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null.");
        }
        else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between 0 and 100.");
        }
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
