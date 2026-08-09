package use_case.comment.delete_comment;

/**
 * Output boundary for deleting a comment.
 */
public interface DeleteCommentOutputBoundary {
    /**
     * Prepares the success view after deleting a comment.
     * @param deleted whether the comment was deleted
     */
    void prepareSuccessView(boolean deleted);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
