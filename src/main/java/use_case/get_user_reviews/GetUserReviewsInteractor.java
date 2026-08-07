package use_case.get_user_reviews;

import java.util.Comparator;
import java.util.List;

import entity.Review;
import use_case.get_media_reviews.ReviewSummaryMapper;

/**
 * Interactor for loading reviews written by one user.
 */
public final class GetUserReviewsInteractor
        implements GetUserReviewsInputBoundary {
    /** The review data access object. */
    private final GetUserReviewsDataAccessInterface reviewDataAccessObject;
    /** The user reviews presenter. */
    private final GetUserReviewsOutputBoundary userReviewsPresenter;

    /**
     * Creates a user reviews interactor without persistence.
     */
    public GetUserReviewsInteractor() {
        this(null, null);
    }

    /**
     * Creates a user reviews interactor with persistence.
     */
    public GetUserReviewsInteractor(
            final GetUserReviewsDataAccessInterface
                    inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /**
     * Creates a user reviews interactor with persistence and presentation.
     */
    public GetUserReviewsInteractor(
            final GetUserReviewsDataAccessInterface inputReviewDataAccessObject,
            final GetUserReviewsOutputBoundary inputUserReviewsPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.userReviewsPresenter = inputUserReviewsPresenter;
    }

    /**
     * Executes the use case and sends output through the output boundary.
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
                    ReviewSummaryMapper.toSummaries(matchingReviews));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (userReviewsPresenter != null) {
                userReviewsPresenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Returns persisted reviews written by one user, ordered newest to oldest.
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
     */
    private void validateOutputBoundary() {
        if (userReviewsPresenter == null) {
            throw new IllegalStateException(
                    "User reviews presenter has not been configured.");
        }
    }

    /**
     * Checks whether a text value is empty or only whitespace.
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Trims a text value, or returns an empty string if it is null.
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
