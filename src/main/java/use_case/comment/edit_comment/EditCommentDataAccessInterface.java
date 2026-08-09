package use_case.comment.edit_comment;

import java.util.Optional;

import entity.Comment;

/**
 * Data access interface for editing comments.
 */
public interface EditCommentDataAccessInterface {
    /**
     * Gets one comment by id.
     * @param commentId the comment id
     * @return the comment, if it exists
     */
    Optional<Comment> getCommentById(String commentId);

    /**
     * Updates one comment.
     * @param commentId the comment id
     * @param newCommentText the replacement comment text
     * @return true if the comment was found and edited
     */
    boolean editComment(String commentId, String newCommentText);
}
