package use_case.comment;

/**
 * Output boundary for creating a comment.
 */
public interface CreateCommentOutputBoundary {
    /**
     * Handles this review or comment operation.
     * @param outputData the outputData
     */
    void prepareSuccessView(CreateCommentOutputData outputData);

    /**
     * Handles this review or comment operation.
     * @param errorMessage the errorMessage
     * @return the result
     */
    String prepareFailView(String errorMessage);
}
