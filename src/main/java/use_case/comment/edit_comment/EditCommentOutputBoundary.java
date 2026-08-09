package use_case.comment.edit_comment;

/**
 * Output boundary for editing a comment.
 */
public interface EditCommentOutputBoundary {
    /**
     * Prepares the success view after editing a comment.
     * @param edited whether the comment was edited
     */
    void prepareSuccessView(boolean edited);

    /**
     * Prepares the failure view after editing a comment.
     * @param errorMessage the error message
     * @return the display-safe error message
     */
    String prepareFailView(String errorMessage);
}
