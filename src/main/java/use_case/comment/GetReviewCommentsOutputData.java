package use_case.comment;

import java.util.ArrayList;
import java.util.List;

import entity.Comment;

/**
 * Output data for loading comments on a review.
 */
public class GetReviewCommentsOutputData {
    private final String reviewId;
    private final List<Comment> comments;

    public GetReviewCommentsOutputData(final String reviewId,
                                       final List<Comment> comments) {
        this.reviewId = reviewId;
        this.comments = new ArrayList<>(comments);
    }

    public String getReviewId() {
        return reviewId;
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }
}
