package use_case.comment.create_comment;

/**
 * Output boundary for creating a comment.
 */
public interface CreateCommentOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(CreateCommentOutputData outputData);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
