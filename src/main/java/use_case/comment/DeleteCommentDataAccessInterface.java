package use_case.comment;

import java.util.Optional;

import entity.Comment;

/**
 * Data access interface for deleting comments.
 */
public interface DeleteCommentDataAccessInterface {

    /**
     * Gets one comment by its id.
     * @param commentId the id of the comment to get
     * @return the comment, if it exists
     */
    Optional<Comment> getCommentById(String commentId);

    /**
     * Deletes one comment.
     * @param commentId the id of the comment to delete
     * @return true if the comment was found and deleted
     */
    boolean deleteComment(String commentId);
}
