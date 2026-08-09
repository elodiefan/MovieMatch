package use_case.comment.edit_comment;

/**
 * Input boundary for editing a comment.
 */
public interface EditCommentInputBoundary {
    /**
     * Edits a comment written by the given user.
     * @param inputData the input data for editing a comment
     */
    void execute(EditCommentInputData inputData);
}
