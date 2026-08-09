package use_case.comment.edit_comment;

/**
 * Input boundary for editing a comment.
 */
public interface EditCommentInputBoundary {
    /**
     * Edits a comment written by the given user.
     * @param commentId the id of the comment to edit
     * @param username the username of the comment author
     * @param newCommentText the updated comment text
     */
    void execute(String commentId, String username, String newCommentText);
}
