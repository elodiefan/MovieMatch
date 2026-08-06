package use_case.comment;

/**
 * Input data for loading comments written by one user.
 */
public final class GetUserCommentsInputData {
    /** The username. */
    private final String username;

    /**
     * Creates input data for loading user comments.
     * @param inputUsername the username whose comments should be loaded
     */
    public GetUserCommentsInputData(final String inputUsername) {
        this.username = inputUsername;
    }

    /**
     * Returns the username whose comments should be loaded.
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
