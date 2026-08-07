package use_case.comment.create_comment;

import entity.Comment;

/** Data access interface for creating comments. */
public interface CreateCommentDataAccessInterface {

    /** Saves a newly created comment. */
    void saveComment(Comment comment);
}
