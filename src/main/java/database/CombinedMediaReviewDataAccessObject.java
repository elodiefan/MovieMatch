package database;

import java.util.ArrayList;
import java.util.List;

import entity.Review;
import use_case.get_media_reviews.GetMediaReviewsDataAccessInterface;

/**
 * Combines external TMDB reviews with MovieMatch reviews.
 */
public final class CombinedMediaReviewDataAccessObject
        implements GetMediaReviewsDataAccessInterface {
    /** External review source. */
    private final GetMediaReviewsDataAccessInterface externalReviewDataAccess;
    /** MovieMatch review source. */
    private final GetMediaReviewsDataAccessInterface localReviewDataAccess;

    /**
     * Creates a combined media review data access object.
     */
    public CombinedMediaReviewDataAccessObject(
            final GetMediaReviewsDataAccessInterface
                    inputExternalReviewDataAccess,
            final GetMediaReviewsDataAccessInterface inputLocalReviewDataAccess) {
        this.externalReviewDataAccess = inputExternalReviewDataAccess;
        this.localReviewDataAccess = inputLocalReviewDataAccess;
    }

    @Override
    public List<Review> getReviewsByMedia(final int mediaId,
                                          final String mediaType) {
        final List<Review> reviews = new ArrayList<>();
        addExternalReviews(reviews, mediaId, mediaType);
        addLocalReviews(reviews, mediaId, mediaType);
        return reviews;
    }

    /**
     * Adds external TMDB reviews when the API is available.
     */
    private void addExternalReviews(final List<Review> reviews,
                                    final int mediaId,
                                    final String mediaType) {
        if (externalReviewDataAccess != null) {
            try {
                reviews.addAll(externalReviewDataAccess.getReviewsByMedia(
                        mediaId, mediaType));
            } catch (IllegalStateException exception) {
                // Keep MovieMatch reviews available when TMDB is offline.
            }
        }
    }

    /**
     * Adds MovieMatch reviews.
     */
    private void addLocalReviews(final List<Review> reviews,
                                 final int mediaId,
                                 final String mediaType) {
        if (localReviewDataAccess != null) {
            reviews.addAll(localReviewDataAccess.getReviewsByMedia(mediaId,
                    mediaType));
        }
    }
}
