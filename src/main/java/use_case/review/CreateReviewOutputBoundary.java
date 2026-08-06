package use_case.review;

/**
 * Output boundary for creating a review.
 */
public interface CreateReviewOutputBoundary {
    void prepareSuccessView(CreateReviewOutputData outputData);

    String prepareFailView(String errorMessage);
}
