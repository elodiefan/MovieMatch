package use_case.like_comment;

/**
 * Data access interface for liking comments.
 */
public interface LikeCommentDataAccessInterface {

    /**
     * Adds a user's like to one comment.
     */
    boolean likeComment(String commentId, String username);
}
