package use_case.review;

import java.util.Iterator;
import java.util.List;

import entity.Review;

/**
 * Interactor for deleting a review.
 */
public class DeleteReviewInteractor {

    /**
     * Deletes one review written by the given user.
     * @param reviewId the id of the review to delete
     * @param username the username of the user deleting the review
     * @param reviews the reviews to search through
     * @return true if the review was deleted
     */
    public boolean deleteReview(final String reviewId, final String username,
                                final List<Review> reviews) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedUsername = trimToEmpty(username);
        validateDeleteReviewData(trimmedReviewId, trimmedUsername, reviews);

        boolean deleted = false;
        final Iterator<Review> reviewIterator = reviews.iterator();
        while (reviewIterator.hasNext() && !deleted) {
            final Review review = reviewIterator.next();
            if (canDeleteReview(review, trimmedReviewId, trimmedUsername)) {
                reviewIterator.remove();
                deleted = true;
            }
        }

        return deleted;
    }

    /**
     * Checks whether the review can be deleted by the user.
     * @param review the review to check
     * @param reviewId the id of the review to delete
     * @param username the username of the user deleting the review
     * @return true if the review matches the id and author
     */
    private boolean canDeleteReview(final Review review, final String reviewId,
                                    final String username) {
        final boolean canDelete;
        if (review == null) {
            canDelete = false;
        } else {
            canDelete = review.getReviewId().equals(reviewId)
                    && review.getAuthorUsername().equals(username);
        }
        return canDelete;
    }

    /**
     * Validates the data needed to delete a review.
     * @param reviewId the review id to validate
     * @param username the username to validate
     * @param reviews the review list to validate
     */
    private void validateDeleteReviewData(final String reviewId,
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
