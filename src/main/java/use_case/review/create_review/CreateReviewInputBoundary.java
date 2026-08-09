package use_case.review.create_review;

/**
 * Input boundary for creating a review.
 */
public interface CreateReviewInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data for creating a review
     */
    void execute(CreateReviewInputData inputData);

    /**
     * Checks whether the user may start writing a review for this media item.
     * @param inputData the input data containing the media and author
     * @return true if the user may write a review
     */
    boolean canCreateReview(CreateReviewInputData inputData);
}
