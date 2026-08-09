package use_case.review.get_user_reviews;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Review;

/**
 * Interactor for loading reviews written by one user.
 */
public final class GetUserReviewsInteractor
        implements GetUserReviewsInputBoundary {
    /**
     * The review data access object.
     */
    private final GetUserReviewsDataAccessInterface reviewDataAccessObject;
    /**
     * The user reviews presenter.
     */
    private final GetUserReviewsOutputBoundary userReviewsPresenter;

    /**
     * Creates a user reviews interactor without persistence.
     */
    public GetUserReviewsInteractor() {
        this(null, null);
    }

    /**
     * Creates a user reviews interactor with persistence.
     * @param inputReviewDataAccessObject the DAO used to load reviews
     */
    public GetUserReviewsInteractor(
            final GetUserReviewsDataAccessInterface
                    inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /**
     * Creates a user reviews interactor with persistence and presentation.
     * @param inputReviewDataAccessObject the DAO used to load reviews
     * @param inputUserReviewsPresenter the output boundary
     */
    public GetUserReviewsInteractor(
            final GetUserReviewsDataAccessInterface inputReviewDataAccessObject,
            final GetUserReviewsOutputBoundary inputUserReviewsPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.userReviewsPresenter = inputUserReviewsPresenter;
    }

    /**
     * Executes the use case and sends output through the output boundary.
     * @param username the username whose reviews are loaded
     */
    @Override
    public void execute(final String username) {
        try {
            validateOutputBoundary();
            final GetUserReviewsInputData inputData =
                    new GetUserReviewsInputData(username);
            final List<Review> matchingReviews =
                    getUserReviews(inputData.getUsername());
            userReviewsPresenter.prepareUserReviewsSuccessView(
                    toOutputData(matchingReviews));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (userReviewsPresenter != null) {
                userReviewsPresenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Returns persisted reviews written by one user, ordered newest to oldest.
     * @param username the username of the review author
     * @return the user's matching reviews
     */
    private List<Review> getUserReviews(final String username) {
        final String trimmedUsername = trimToEmpty(username);
        validateUsername(trimmedUsername);

        final List<Review> matchingReviews =
                reviewDataAccessObject.getReviewsByUsername(trimmedUsername);
        matchingReviews.sort(Comparator.comparing(Review::getCreatedAt)
                .reversed());
        return matchingReviews;
    }

    /**
     * Validates the username needed to load persisted reviews.
     * @param username the username to validate
     * @throws IllegalArgumentException if the username is blank
     * @throws IllegalStateException if the review DAO is not configured
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
     * Validates that the output boundary has been configured.
     * @throws IllegalStateException if the presenter is not configured
     */
    private void validateOutputBoundary() {
        if (userReviewsPresenter == null) {
            throw new IllegalStateException(
                    "User reviews presenter has not been configured.");
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

    private GetUserReviewsOutputData toOutputData(final List<Review> reviews) {
        final List<GetUserReviewsOutputData.UserReviewData> outputReviews =
                new ArrayList<>();
        for (Review review : reviews) {
            outputReviews.add(new GetUserReviewsOutputData.UserReviewData(
                    review.getReviewId(), review.getMediaId(),
                    review.getMediaType(), review.getMediaTitle(),
                    review.getReleaseYear(), review.getPosterPath(),
                    review.getRating(), review.getReviewText(),
                    review.getCreatedAt(), review.getUpdatedAt(),
                    review.getLikeCount()));
        }
        return new GetUserReviewsOutputData(outputReviews);
    }
}
