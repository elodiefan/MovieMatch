package use_case.review;

/**
 * Output boundary for editing a review.
 */
public interface EditReviewOutputBoundary {
    void prepareSuccessView(EditReviewOutputData outputData);

    String prepareFailView(String errorMessage);
}
