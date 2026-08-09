package use_case.comment.like_comment;

/**
 * Output boundary for liking a comment.
 */
public interface LikeCommentOutputBoundary {
    /**
     * Prepares the success view after liking a comment.
     * @param liked whether the comment was liked
     */
    void prepareSuccessView(boolean liked);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
