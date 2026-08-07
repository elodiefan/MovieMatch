package use_case.comment.get_review_comments;

import java.util.ArrayList;
import java.util.List;

import entity.Comment;

/** Output data for loading comments on a review. */
public final class GetReviewCommentsOutputData {
    /** The review id. */
    private final String reviewId;
    /** The comments. */
    private final List<Comment> comments;

    /** Handles this review or comment operation. */
    public GetReviewCommentsOutputData(final String inputReviewId,
                                       final List<Comment> inputComments) {
        this.reviewId = inputReviewId;
        this.comments = new ArrayList<>(inputComments);
    }

    /** Handles this review or comment operation. */
    public String getReviewId() {
        return reviewId;
    }

    /** Handles this review or comment operation. */
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }
}
