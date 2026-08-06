package use_case.comment;

/**
 * Input data for loading comments written by one user.
 */
public class GetUserCommentsInputData {
    private final String username;

    /**
     * Creates input data for loading user comments.
     * @param username the username whose comments should be loaded
     */
    public GetUserCommentsInputData(final String username) {
        this.username = username;
    }

    /**
     * Returns the username whose comments should be loaded.
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
