package use_case.unlike_comment;

/**
 * Data access interface for unliking comments.
 */
public interface UnlikeCommentDataAccessInterface {

    /**
     * Removes a user's like from one comment.
     * @param commentId the id of the comment to unlike
     * @param username the username of the user unliking the comment
     * @return true if the comment was found and unliked
     */
    boolean unlikeComment(String commentId, String username);
}
