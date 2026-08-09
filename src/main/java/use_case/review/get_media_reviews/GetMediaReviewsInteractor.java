package use_case.review.get_media_reviews;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Review;

/**
 * Interactor for loading reviews for one media item.
 */
public final class GetMediaReviewsInteractor
        implements GetMediaReviewsInputBoundary {
    /**
     * Smallest valid media id.
     */
    private static final int MIN_MEDIA_ID = 0;

    /**
     * The review data access object.
     */
    private final GetMediaReviewsDataAccessInterface reviewDataAccessObject;
    /**
     * The presenter.
     */
    private final GetMediaReviewsOutputBoundary presenter;

    /**
     * Creates a media reviews interactor without persistence.
     */
    public GetMediaReviewsInteractor() {
        this(null, null);
    }

    /**
     * Creates a media reviews interactor with persistence.
     * @param inputReviewDataAccessObject the DAO used to load reviews
     */
    public GetMediaReviewsInteractor(
            final GetMediaReviewsDataAccessInterface
                    inputReviewDataAccessObject) {
        this(inputReviewDataAccessObject, null);
    }

    /**
     * Handles this review or comment operation.
     * @param inputReviewDataAccessObject the inputReviewDataAccessObject
     * @param inputPresenter the inputPresenter
     */
    public GetMediaReviewsInteractor(
            final GetMediaReviewsDataAccessInterface
                    inputReviewDataAccessObject,
            final GetMediaReviewsOutputBoundary inputPresenter) {
        this.reviewDataAccessObject = inputReviewDataAccessObject;
        this.presenter = inputPresenter;
    }

    @Override
    public void execute(final int mediaId, final String mediaType) {
        try {
            validatePresenter();
            final GetMediaReviewsInputData inputData =
                    new GetMediaReviewsInputData(mediaId, mediaType);
            final List<Review> reviews = getMediaReviews(
                    inputData.getMediaId(), inputData.getMediaType());
            presenter.prepareSuccessView(toOutputData(reviews));
        } catch (IllegalArgumentException | IllegalStateException error) {
            if (presenter != null) {
                presenter.prepareFailView(error.getMessage());
            }
        }
    }

    /**
     * Returns persisted reviews for one media item, ordered newest to oldest.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @return the matching media reviews
     */
    private List<Review> getMediaReviews(final int mediaId,
                                        final String mediaType) {
        final String trimmedMediaType = trimToEmpty(mediaType);
        validateGetMediaReviewsData(mediaId, trimmedMediaType);

        final List<Review> matchingReviews =
                reviewDataAccessObject.getReviewsByMedia(mediaId,
                        trimmedMediaType);
        matchingReviews.sort(Comparator.comparing(Review::getCreatedAt)
                .reversed());
        return matchingReviews;
    }

    /**
     * Validates data needed to load persisted media reviews.
     * @param mediaId the media id to validate
     * @param mediaType the media type to validate
     * @throws IllegalArgumentException if the media id or type is invalid
     * @throws IllegalStateException if the review DAO is not configured
     */
    private void validateGetMediaReviewsData(final int mediaId,
                                             final String mediaType) {
        if (mediaId < MIN_MEDIA_ID) {
            throw new IllegalArgumentException("Media id cannot be negative.");
        } else if (isBlank(mediaType)) {
            throw new IllegalArgumentException("Media type cannot be empty.");
        } else if (reviewDataAccessObject == null) {
            throw new IllegalStateException(
                    "Review data access object has not been configured.");
        }
    }

    private void validatePresenter() {
        if (presenter == null) {
            throw new IllegalStateException(
                    "Media reviews presenter has not been configured.");
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

    private GetMediaReviewsOutputData toOutputData(
            final List<Review> reviews) {
        final List<GetMediaReviewsOutputData.MediaReviewData> outputReviews =
                new ArrayList<>();
        for (Review review : reviews) {
            outputReviews.add(new GetMediaReviewsOutputData.MediaReviewData(
                    review.getReviewId(), review.getAuthorUsername(),
                    review.getAuthorDisplayName(), review.getRating(),
                    review.getReviewText(), review.getCreatedAt(),
                    review.getUpdatedAt(), review.getLikeCount(),
                    review.getLikedByUsernames(), review.getSource()));
        }
        return new GetMediaReviewsOutputData(outputReviews);
    }
}
