package use_case.review;

import java.util.List;

import entity.Review;

/**
 * Data access interface for loading reviews for one media item.
 */
public interface GetMediaReviewsDataAccessInterface {

    /**
     * Gets all reviews for one media item.
     * @param mediaId the media id
     * @param mediaType the media type
     * @return the reviews for the media item
     */
    List<Review> getReviewsByMedia(int mediaId, String mediaType);
}
