package use_case.comment;

/**
 * Output boundary for deleting a comment.
 */
public interface DeleteCommentOutputBoundary {
    void prepareSuccessView(DeleteCommentOutputData outputData);

    String prepareFailView(String errorMessage);
}
