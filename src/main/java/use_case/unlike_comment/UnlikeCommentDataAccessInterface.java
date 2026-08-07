package use_case.unlike_comment;

/**
 * Data access interface for unliking comments.
 */
public interface UnlikeCommentDataAccessInterface {

    /**
     * Removes a user's like from one comment.
     */
    boolean unlikeComment(String commentId, String username);
}
