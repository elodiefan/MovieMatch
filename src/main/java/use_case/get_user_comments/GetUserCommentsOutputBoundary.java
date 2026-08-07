package use_case.get_user_comments;

/**
 * Output boundary for loading comments written by one user.
 */
public interface GetUserCommentsOutputBoundary {
    /**
     * Prepares the success view.
     */
    void prepareUserCommentsSuccessView(GetUserCommentsOutputData outputData);

    /**
     * Prepares the failure view.
     */
    String prepareFailView(String errorMessage);
}
