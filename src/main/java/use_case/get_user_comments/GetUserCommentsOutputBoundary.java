package use_case.get_user_comments;

import java.util.List;

/**
 * Output boundary for loading comments written by one user.
 */
public interface GetUserCommentsOutputBoundary {
    /**
     * Prepares the success view.
     */
    void prepareUserCommentsSuccessView(List<UserCommentSummaryData> comments);

    /**
     * Prepares the failure view.
     */
    String prepareFailView(String errorMessage);
}
