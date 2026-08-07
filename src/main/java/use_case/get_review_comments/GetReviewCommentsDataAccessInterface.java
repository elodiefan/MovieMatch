package use_case.get_review_comments;

import java.util.List;

import entity.Comment;

/**
 * Data access interface for loading comments on a review.
 */
public interface GetReviewCommentsDataAccessInterface {

    /**
     * Gets all comments for one review.
     */
    List<Comment> getCommentsByReviewId(String reviewId);
}
