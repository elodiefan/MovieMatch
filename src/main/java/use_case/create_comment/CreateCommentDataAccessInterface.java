package use_case.create_comment;

import entity.Comment;

/**
 * Data access interface for creating comments.
 */
public interface CreateCommentDataAccessInterface {

    /**
     * Saves a newly created comment.
     * @param comment the comment to save
     */
    void saveComment(Comment comment);
}
