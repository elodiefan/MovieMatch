package use_case.comment;

/**
 * Output boundary for deleting a comment.
 */
public interface DeleteCommentOutputBoundary {
    /**
     * Handles this review or comment operation.
     * @param outputData the outputData
     */
    void prepareSuccessView(DeleteCommentOutputData outputData);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
