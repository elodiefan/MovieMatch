package use_case.comment.like_comment;

/**
 * Input boundary for liking a comment.
 */
public interface LikeCommentInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data
     */
    void execute(LikeCommentInputData inputData);
}
