package use_case.get_user_comments;

/**
 * Input data for loading comments written by one user.
 */
public final class GetUserCommentsInputData {
    /** The username. */
    private final String username;

    /**
     * Creates input data for loading user comments.
     */
    public GetUserCommentsInputData(final String inputUsername) {
        this.username = inputUsername;
    }

    /**
     * Returns the username whose comments should be loaded.
     */
    public String getUsername() {
        return username;
    }
}
