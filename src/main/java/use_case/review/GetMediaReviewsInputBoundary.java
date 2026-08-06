package use_case.review;

/**
 * Input boundary for loading reviews for one media item.
 */
public interface GetMediaReviewsInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(GetMediaReviewsInputData inputData);
}
