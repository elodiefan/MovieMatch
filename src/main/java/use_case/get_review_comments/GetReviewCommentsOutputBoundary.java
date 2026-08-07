package use_case.get_review_comments;

import java.util.List;

import use_case.get_review_comments.CommentSummaryData;

/**
 * Output boundary for loading comments on a review.
 */
public interface GetReviewCommentsOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(String reviewId, List<CommentSummaryData> comments);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
