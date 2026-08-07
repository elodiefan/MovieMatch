package use_case.review.create_review;

/** Output boundary for creating a review. */
public interface CreateReviewOutputBoundary {
    /** Handles this review or comment operation. */
    void prepareSuccessView(CreateReviewOutputData outputData);

    /** Handles this review or comment operation. */
    String prepareFailView(String errorMessage);
}
