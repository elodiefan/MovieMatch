package use_case.review.create_review;

/**
 * Input boundary for creating a review.
 */
public interface CreateReviewInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(CreateReviewInputData inputData);
}
