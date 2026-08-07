package use_case.comment.delete_comment;

/** Output boundary for deleting a comment. */
public interface DeleteCommentOutputBoundary {
    /** Handles this review or comment operation. */
    void prepareSuccessView(DeleteCommentOutputData outputData);

    /** Handles this review or comment operation. */
    String prepareFailView(String errorMessage);
}
