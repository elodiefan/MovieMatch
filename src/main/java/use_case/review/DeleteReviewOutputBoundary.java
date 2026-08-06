package use_case.review;

/**
 * Output boundary for deleting a review.
 */
public interface DeleteReviewOutputBoundary {
    void prepareSuccessView(DeleteReviewOutputData outputData);

    String prepareFailView(String errorMessage);
}
