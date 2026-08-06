package use_case.comment;

/**
 * Output boundary for creating a comment.
 */
public interface CreateCommentOutputBoundary {
    void prepareSuccessView(CreateCommentOutputData outputData);

    String prepareFailView(String errorMessage);
}
