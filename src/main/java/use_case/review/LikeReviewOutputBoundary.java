package use_case.review;

/**
 * Output boundary for liking a review.
 */
public interface LikeReviewOutputBoundary {
    void prepareSuccessView(LikeReviewOutputData outputData);

    String prepareFailView(String errorMessage);
}
