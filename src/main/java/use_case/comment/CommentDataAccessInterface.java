package use_case.comment;

import java.util.List;
import java.util.Optional;

import entity.Comment;

/**
 * Data access interface for comment persistence.
 */
public interface CommentDataAccessInterface {

    /**
     * Saves a comment.
     */
    void saveComment(Comment comment);

    /**
     * Checks whether a comment exists by its id.
     */
    boolean existsByCommentId(String commentId);

    /**
     * Gets one comment by its id.
     */
    Optional<Comment> getCommentById(String commentId);

    /**
     * Gets all comments for one review.
     */
    List<Comment> getCommentsByReviewId(String reviewId);

    /**
     * Gets all comments written by one user.
     */
    List<Comment> getCommentsByUsername(String username);

    /**
     * Gets all replies to one parent comment.
     */
    List<Comment> getRepliesByParentCommentId(String parentCommentId);

    /**
     * Updates the text of one comment.
     */
    boolean editComment(String commentId, String newCommentText);

    /**
     * Deletes one comment.
     */
    boolean deleteComment(String commentId);

    /**
     * Adds a user's like to one comment.
     */
    boolean likeComment(String commentId, String username);

    /**
     * Removes a user's like from one comment.
     */
    boolean unlikeComment(String commentId, String username);

    /**
     * Gets all stored comments.
     */
    List<Comment> getAllComments();
}
