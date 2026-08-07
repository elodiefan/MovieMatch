package use_case.review.get_user_reviews;

/** Input data for loading reviews written by one user. */
public final class GetUserReviewsInputData {
    /** The username. */
    private final String username;

    /** Creates input data for loading user reviews. */
    public GetUserReviewsInputData(final String inputUsername) {
        this.username = inputUsername;
    }

    /** Returns the username whose reviews should be loaded. */
    public String getUsername() {
        return username;
    }
}
