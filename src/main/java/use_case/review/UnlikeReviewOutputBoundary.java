package use_case.review;

/**
 * Output boundary for unliking a review.
 */
public interface UnlikeReviewOutputBoundary {
    void prepareSuccessView(UnlikeReviewOutputData outputData);

    String prepareFailView(String errorMessage);
}
