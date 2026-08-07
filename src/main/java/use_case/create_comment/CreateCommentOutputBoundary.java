package use_case.create_comment;

/**
 * Output boundary for creating a comment.
 */
public interface CreateCommentOutputBoundary {
    /**
     * Handles this review or comment operation.
     */
    void prepareSuccessView(boolean created);

    /**
     * Handles this review or comment operation.
     */
    String prepareFailView(String errorMessage);
}
