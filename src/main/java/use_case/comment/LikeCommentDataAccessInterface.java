package use_case.comment;

/**
 * Data access interface for liking comments.
 */
public interface LikeCommentDataAccessInterface {

    /**
     * Adds a user's like to one comment.
     * @param commentId the id of the comment to like
     * @param username the username of the user liking the comment
     * @return true if the comment was found and liked
     */
    boolean likeComment(String commentId, String username);
}
