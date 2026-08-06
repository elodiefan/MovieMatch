package use_case.review;

/**
 * Output boundary for loading reviews for one media item.
 */
public interface GetMediaReviewsOutputBoundary {
    void prepareSuccessView(GetMediaReviewsOutputData outputData);

    String prepareFailView(String errorMessage);
}
