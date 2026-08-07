package use_case.like_comment;

/**
 * Output boundary for liking a comment.
 */
public interface LikeCommentOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(boolean liked);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
