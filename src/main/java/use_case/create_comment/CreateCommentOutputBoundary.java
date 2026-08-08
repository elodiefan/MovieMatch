package use_case.create_comment;

/**
 * Output boundary for creating a comment.
 */
public interface CreateCommentOutputBoundary {
    /**
     * Prepares the success view after creating a comment.
     * @param created whether the comment was created
     */
    void prepareSuccessView(boolean created);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
