package use_case.unlike_comment;

/**
 * Output boundary for unliking a comment.
 */
public interface UnlikeCommentOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(boolean unliked);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
