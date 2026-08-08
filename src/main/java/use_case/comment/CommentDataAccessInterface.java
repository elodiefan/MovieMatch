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
     * @param comment the comment to save
     */
    void saveComment(Comment comment);

    /**
     * Checks whether a comment exists by its id.
     * @param commentId the id of the comment to check
     * @return true if the comment exists
     */
    boolean existsByCommentId(String commentId);

    /**
     * Gets one comment by its id.
     * @param commentId the id of the comment to get
     * @return the comment, if it exists
     */
    Optional<Comment> getCommentById(String commentId);

    /**
     * Gets all comments for one review.
     * @param reviewId the id of the review
     * @return the comments for the review
     */
    List<Comment> getCommentsByReviewId(String reviewId);

    /**
     * Gets all comments written by one user.
     * @param username the author's username
     * @return the comments written by the user
     */
    List<Comment> getCommentsByUsername(String username);

    /**
     * Gets all replies to one parent comment.
     * @param parentCommentId the id of the parent comment
     * @return the replies to the parent comment
     */
    List<Comment> getRepliesByParentCommentId(String parentCommentId);

    /**
     * Updates the text of one comment.
     * @param commentId the id of the comment to edit
     * @param newCommentText the replacement comment text
     * @return true if the comment was found and edited
     */
    boolean editComment(String commentId, String newCommentText);

    /**
     * Deletes one comment.
     * @param commentId the id of the comment to delete
     * @return true if the comment was found and deleted
     */
    boolean deleteComment(String commentId);

    /**
     * Adds a user's like to one comment.
     * @param commentId the id of the comment to like
     * @param username the username of the user liking the comment
     * @return true if the comment was found and liked
     */
    boolean likeComment(String commentId, String username);

    /**
     * Removes a user's like from one comment.
     * @param commentId the id of the comment to unlike
     * @param username the username of the user unliking the comment
     * @return true if the comment was found and unliked
     */
    boolean unlikeComment(String commentId, String username);

    /**
     * Gets all stored comments.
     * @return all stored comments
     */
    List<Comment> getAllComments();
}
