package use_case.comment;

import java.util.List;
import java.util.Optional;

import entity.Comment;
import use_case.comment.create_comment.CreateCommentDataAccessInterface;
import use_case.comment.delete_comment.DeleteCommentDataAccessInterface;
import use_case.comment.get_review_comments.GetReviewCommentsDataAccessInterface;
import use_case.comment.get_user_comments.GetUserCommentsDataAccessInterface;
import use_case.comment.like_comment.LikeCommentDataAccessInterface;
import use_case.comment.unlike_comment.UnlikeCommentDataAccessInterface;

/**
 * Data access interface for comment persistence.
 */
public interface CommentDataAccessInterface extends
        CreateCommentDataAccessInterface,
        DeleteCommentDataAccessInterface,
        GetReviewCommentsDataAccessInterface,
        GetUserCommentsDataAccessInterface,
        LikeCommentDataAccessInterface,
        UnlikeCommentDataAccessInterface {

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
