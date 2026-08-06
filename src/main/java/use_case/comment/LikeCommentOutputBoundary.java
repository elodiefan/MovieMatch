package use_case.comment;

/**
 * Output boundary for liking a comment.
 */
public interface LikeCommentOutputBoundary {
    void prepareSuccessView(LikeCommentOutputData outputData);

    String prepareFailView(String errorMessage);
}
