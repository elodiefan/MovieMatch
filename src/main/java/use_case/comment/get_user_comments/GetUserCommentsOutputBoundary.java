package use_case.comment.get_user_comments;

/**
 * Output boundary for loading comments written by one user.
 */
public interface GetUserCommentsOutputBoundary {
    /**
     * Prepares the success view.
     * @param outputData the output data
     */
    void prepareSuccessView(GetUserCommentsOutputData outputData);

    /**
     * Prepares the failure view.
     * @param errorMessage the error message
     * @return the display-safe error message
     */
    String prepareFailView(String errorMessage);
}
