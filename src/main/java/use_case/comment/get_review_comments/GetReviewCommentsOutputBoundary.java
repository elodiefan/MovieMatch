package use_case.comment.get_review_comments;

/** Output boundary for loading comments on a review. */
public interface GetReviewCommentsOutputBoundary {
    /** Handles this review or comment operation. */
    void prepareSuccessView(GetReviewCommentsOutputData outputData);

    /** Handles this review or comment operation. */
    String prepareFailView(String errorMessage);
}
