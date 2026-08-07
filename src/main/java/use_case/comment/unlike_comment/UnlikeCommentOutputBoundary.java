package use_case.comment.unlike_comment;

/** Output boundary for unliking a comment. */
public interface UnlikeCommentOutputBoundary {
    /** Handles this review or comment operation. */
    void prepareSuccessView(UnlikeCommentOutputData outputData);

    /** Handles this review or comment operation. */
    String prepareFailView(String errorMessage);
}
