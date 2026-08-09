package use_case.review.get_media_reviews;

/**
 * Input boundary for loading reviews for one media item.
 */
public interface GetMediaReviewsInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data for loading media reviews
     */
    void execute(GetMediaReviewsInputData inputData);
}
