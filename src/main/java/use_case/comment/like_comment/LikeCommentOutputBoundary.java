package use_case.comment.like_comment;

/**
 * Output boundary for liking a comment.
 */
public interface LikeCommentOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(LikeCommentOutputData outputData);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
