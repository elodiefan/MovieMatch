package use_case.review;

/**
 * Output boundary for loading reviews for one media item.
 */
public interface GetMediaReviewsOutputBoundary {
    /**
     * Handles this review or comment operation.
     * @param outputData the outputData
     */
    void prepareSuccessView(GetMediaReviewsOutputData outputData);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
