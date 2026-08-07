package use_case.comment.get_user_comments;

/**
 * Output boundary for loading comments written by one user.
 */
public interface GetUserCommentsOutputBoundary {
    /**
     * Prepares the success view.
     */
    void prepareSuccessView(GetUserCommentsOutputData outputData);

    /**
     * Prepares the failure view.
     */
    String prepareFailView(String errorMessage);
}
