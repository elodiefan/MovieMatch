package use_case.review;

import java.util.List;

import entity.Review;

/**
 * Interactor for liking a review.
 */
public class LikeReviewInteractor {
    private final LikeReviewDataAccessInterface reviewDataAccessObject;

    /**
     * Creates a like review interactor without persistence.
     */
    public LikeReviewInteractor() {
        this(null);
    }

    /**
     * Creates a like review interactor with persistence.
     * @param reviewDataAccessObject the DAO used to like reviews
     */
    public LikeReviewInteractor(
            final LikeReviewDataAccessInterface reviewDataAccessObject) {
        this.reviewDataAccessObject = reviewDataAccessObject;
    }

    /**
     * Adds a user's like to a persisted review.
     * @param reviewId the id of the review to like
     * @param username the username of the user liking the review
     * @return true if the review was found and liked
     */
    public boolean likeReview(final String reviewId, final String username) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedUsername = trimToEmpty(username);
        validateLikeReviewData(trimmedReviewId, trimmedUsername);
        return reviewDataAccessObject.likeReview(trimmedReviewId,
                trimmedUsername);
    }

    /**
     * Adds a user's like to a review.
     * @param reviewId the id of the review to like
     * @param username the username of the user liking the review
     * @param reviews the reviews to search through
     * @return true if the review was found and liked
     */
    public boolean likeReview(final String reviewId, final String username,
                              final List<Review> reviews) {
        final String trimmedReviewId = trimToEmpty(reviewId);
        final String trimmedUsername = trimToEmpty(username);
        validateLikeReviewData(trimmedReviewId, trimmedUsername, reviews);

        boolean liked = false;
        for (Review review : reviews) {
            if (isMatchingReview(review, trimmedReviewId)) {
                review.like(trimmedUsername);
                liked = true;
            }
        }

        return liked;
    }

    /**
     * Validates the data needed to like a persisted review.
     * @param reviewId the review id to validate
     * @param username the username to validate
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
     * Validates the data needed to like a review.
     * @param reviewId the review id to validate
     * @param username the username to validate
     * @param reviews the review list to validate
     */
    private void validateLikeReviewData(final String reviewId,
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
