package use_case.get_media_reviews;

/**
 * Output boundary for loading reviews for one media item.
 */
public interface GetMediaReviewsOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(GetMediaReviewsOutputData outputData);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
