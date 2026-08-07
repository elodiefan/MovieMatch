package use_case.comment.unlike_comment;

/**
 * Input boundary for unliking a comment.
 */
public interface UnlikeCommentInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(UnlikeCommentInputData inputData);
}
