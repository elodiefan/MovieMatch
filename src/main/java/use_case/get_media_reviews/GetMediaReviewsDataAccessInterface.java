package use_case.get_media_reviews;

import java.util.List;

import entity.Review;

/**
 * Data access interface for loading reviews for one media item.
 */
public interface GetMediaReviewsDataAccessInterface {

    /**
     * Gets all reviews for one media item.
     */
    List<Review> getReviewsByMedia(int mediaId, String mediaType);
}
