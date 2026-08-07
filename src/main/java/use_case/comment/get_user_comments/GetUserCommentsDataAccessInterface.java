package use_case.comment.get_user_comments;

import java.util.List;

import entity.Comment;

/** Data access interface for loading comments written by one user. */
public interface GetUserCommentsDataAccessInterface {

    /** Gets all comments written by one user. */
    List<Comment> getCommentsByUsername(String username);
}
