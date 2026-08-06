package use_case.review;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Review;

/**
 * Interactor for loading reviews for one media item.
 */
public class GetMediaReviewsInteractor {
    /**
     * Smallest valid media id.
     */
    private static final int MIN_MEDIA_ID = 0;

    private final GetMediaReviewsDataAccessInterface reviewDataAccessObject;

    /**
     * Creates a media reviews interactor without persistence.
     */
    public GetMediaReviewsInteractor() {
        this(null);
    }

    /**
     * Creates a media reviews interactor with persistence.
     * @param reviewDataAccessObject the DAO used to load reviews
     */
    public GetMediaReviewsInteractor(
            final GetMediaReviewsDataAccessInterface reviewDataAccessObject) {
        this.reviewDataAccessObject = reviewDataAccessObject;
    }

    /**
     * Returns persisted reviews for one media item, ordered newest to oldest.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @return the matching media reviews
     */
    public List<Review> getMediaReviews(final int mediaId,
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
     * Returns the reviews for one media item, ordered from newest to oldest.
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @param reviews the reviews to search through
     * @return the matching media reviews
     */
    public List<Review> getMediaReviews(final int mediaId,
                                        final String mediaType,
                                        final List<Review> reviews) {
        final String trimmedMediaType = trimToEmpty(mediaType);
        validateGetMediaReviewsData(mediaId, trimmedMediaType, reviews);

        final List<Review> matchingReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (isMatchingMediaReview(review, mediaId, trimmedMediaType)) {
                matchingReviews.add(review);
            }
        }

        matchingReviews.sort(Comparator.comparing(Review::getCreatedAt)
                .reversed());
        return matchingReviews;
    }

    /**
     * Validates data needed to load persisted media reviews.
     * @param mediaId the media id to validate
     * @param mediaType the media type to validate
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

    /**
     * Checks whether a review belongs to the requested media item.
     * @param review the review to check
     * @param mediaId the reviewed media's identifier
     * @param mediaType the reviewed media's type
     * @return true if the review belongs to the requested media item
     */
    private boolean isMatchingMediaReview(final Review review,
                                          final int mediaId,
                                          final String mediaType) {
        final boolean matchingReview;
        if (review == null) {
            matchingReview = false;
        } else {
            matchingReview = review.getMediaId() == mediaId
                    && review.getMediaType().equals(mediaType);
        }
        return matchingReview;
    }

    /**
     * Validates the data needed to load media reviews.
     * @param mediaId the media id to validate
     * @param mediaType the media type to validate
     * @param reviews the review list to validate
     */
    private void validateGetMediaReviewsData(final int mediaId,
                                             final String mediaType,
                                             final List<Review> reviews) {
        if (mediaId < MIN_MEDIA_ID) {
            throw new IllegalArgumentException("Media id cannot be negative.");
        } else if (isBlank(mediaType)) {
            throw new IllegalArgumentException("Media type cannot be empty.");
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
