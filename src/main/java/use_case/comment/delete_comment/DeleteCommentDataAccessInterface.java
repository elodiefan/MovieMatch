package use_case.comment.delete_comment;

import java.util.Optional;

import entity.Comment;

/**
 * Data access interface for deleting comments.
 */
public interface DeleteCommentDataAccessInterface {

    /**
     * Gets one comment by its id.
     */
    Optional<Comment> getCommentById(String commentId);

    /**
     * Deletes one comment.
     */
    boolean deleteComment(String commentId);
}
