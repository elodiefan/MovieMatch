package use_case.comment.get_review_comments;

import java.util.ArrayList;
import java.util.List;

import entity.Comment;
import use_case.comment.CommentSummaryData;
import use_case.comment.CommentSummaryMapper;

/**
 * Output data for loading comments on a review.
 */
public final class GetReviewCommentsOutputData {
    /** The review id. */
    private final String reviewId;
    /** The comments. */
    private final List<CommentSummaryData> comments;

    /**
     * Handles this review or comment operation.
     */
    public GetReviewCommentsOutputData(final String inputReviewId,
                                       final List<Comment> inputComments) {
        this.reviewId = inputReviewId;
        this.comments = CommentSummaryMapper.toSummaries(inputComments);
    }

    /**
     * Handles this review or comment operation.
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Handles this review or comment operation.
     */
    public List<CommentSummaryData> getComments() {
        return new ArrayList<>(comments);
    }
}
