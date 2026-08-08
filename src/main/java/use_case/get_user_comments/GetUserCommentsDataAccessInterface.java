package use_case.get_user_comments;

import java.util.List;

import entity.Comment;

/**
 * Data access interface for loading comments written by one user.
 */
public interface GetUserCommentsDataAccessInterface {

    /**
     * Gets all comments written by one user.
     * @param username the author's username
     * @return the comments written by the user
     */
    List<Comment> getCommentsByUsername(String username);
}
