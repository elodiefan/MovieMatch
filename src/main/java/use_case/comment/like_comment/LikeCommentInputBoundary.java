package use_case.comment.like_comment;

/** Input boundary for liking a comment. */
public interface LikeCommentInputBoundary {
    /** Executes the use case. */
    void execute(LikeCommentInputData inputData);
}
