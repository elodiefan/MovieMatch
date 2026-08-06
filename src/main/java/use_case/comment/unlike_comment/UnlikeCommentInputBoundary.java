package use_case.comment.unlike_comment;

/**
 * Input boundary for unliking a comment.
 */
public interface UnlikeCommentInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(UnlikeCommentInputData inputData);
}
