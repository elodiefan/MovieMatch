package use_case.review;

/**
 * Input data for loading reviews written by one user.
 */
public class GetUserReviewsInputData {
    private final String username;

    /**
     * Creates input data for loading user reviews.
     * @param username the username whose reviews should be loaded
     */
    public GetUserReviewsInputData(final String username) {
        this.username = username;
    }

    /**
     * Returns the username whose reviews should be loaded.
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
