package use_case.review.get_media_reviews;

/**
 * Input boundary for loading reviews for one media item.
 */
public interface GetMediaReviewsInputBoundary {
    /**
     * Executes the use case.
     * @param mediaId the media id
     * @param mediaType the media type
     */
    void execute(int mediaId, String mediaType);
}
