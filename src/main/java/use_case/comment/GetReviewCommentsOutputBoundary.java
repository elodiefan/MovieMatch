package use_case.comment;

/**
 * Output boundary for loading comments on a review.
 */
public interface GetReviewCommentsOutputBoundary {
    void prepareSuccessView(GetReviewCommentsOutputData outputData);

    String prepareFailView(String errorMessage);
}
