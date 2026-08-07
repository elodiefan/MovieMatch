package use_case.get_user_reviews;

/**
 * Input boundary for loading reviews written by one user.
 */
public interface GetUserReviewsInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(String username);
}
