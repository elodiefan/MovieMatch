package use_case.comment.like_comment;

/**
 * Input boundary for liking a comment.
 */
public interface LikeCommentInputBoundary {
    /**
     * Executes the use case.
     * @param inputData the input data for liking a comment
     */
    void execute(LikeCommentInputData inputData);
}
