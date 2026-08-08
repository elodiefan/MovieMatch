package use_case.unlike_comment;

/**
 * Output boundary for unliking a comment.
 */
public interface UnlikeCommentOutputBoundary {
    /**
     * Prepares the success view after unliking a comment.
     * @param unliked whether the comment was unliked
     */
    void prepareSuccessView(boolean unliked);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
