package use_case.comment.get_review_comments;

/**
 * Output boundary for loading comments on a review.
 */
public interface GetReviewCommentsOutputBoundary {
    /**
     * Handles this review or comment operation.
     * @param outputData the outputData
     */
    void prepareSuccessView(GetReviewCommentsOutputData outputData);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
