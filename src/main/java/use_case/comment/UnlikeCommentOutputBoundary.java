package use_case.comment;

/**
 * Output boundary for unliking a comment.
 */
public interface UnlikeCommentOutputBoundary {
    void prepareSuccessView(UnlikeCommentOutputData outputData);

    String prepareFailView(String errorMessage);
}
