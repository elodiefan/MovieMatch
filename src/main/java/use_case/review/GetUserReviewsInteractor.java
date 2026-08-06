package use_case.review;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Review;

/**
 * Interactor for loading reviews written by one user.
 */
public class GetUserReviewsInteractor {
    private final GetUserReviewsDataAccessInterface reviewDataAccessObject;

    /**
     * Creates a user reviews interactor without persistence.
     */
    public GetUserReviewsInteractor() {
        this(null);
    }

    /**
     * Creates a user reviews interactor with persistence.
     * @param reviewDataAccessObject the DAO used to load reviews
     */
    public GetUserReviewsInteractor(
            final GetUserReviewsDataAccessInterface reviewDataAccessObject) {
        this.reviewDataAccessObject = reviewDataAccessObject;
    }

    /**
     * Returns persisted reviews written by one user, ordered newest to oldest.
     * @param username the username of the review author
     * @return the user's matching reviews
     */
    public List<Review> getUserReviews(final String username) {
        final String trimmedUsername = trimToEmpty(username);
        validateUsername(trimmedUsername);

        final List<Review> matchingReviews =
                reviewDataAccessObject.getReviewsByUsername(trimmedUsername);
        matchingReviews.sort(Comparator.comparing(Review::getCreatedAt)
                .reversed());
        return matchingReviews;
    }

    /**
     * Returns the reviews written by one user, ordered from newest to oldest.
     * @param username the username of the review author
     * @param reviews the reviews to search through
     * @return the user's matching reviews
     */
    public List<Review> getUserReviews(final String username,
                                       final List<Review> reviews) {
        final String trimmedUsername = trimToEmpty(username);
        validateGetUserReviewsData(trimmedUsername, reviews);

        final List<Review> matchingReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getAuthorUsername().equals(trimmedUsername)) {
                matchingReviews.add(review);
            }
        }

        matchingReviews.sort(Comparator.comparing(Review::getCreatedAt)
                .reversed());
        return matchingReviews;
    }

    /**
     * Validates the username needed to load persisted reviews.
     * @param username the username to validate
     */
    private void validateUsername(final String username) {
        if (isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        } else if (reviewDataAccessObject == null) {
            throw new IllegalStateException(
                    "Review data access object has not been configured.");
        }
    }

    /**
     * Validates the data needed to load a user's reviews.
     * @param username the username to validate
     * @param reviews the review list to validate
     */
    private void validateGetUserReviewsData(final String username,
                                            final List<Review> reviews) {
        if (isBlank(username)) {
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
