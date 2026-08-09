package interface_adapter.search_user;

/**
 * Display row for one user search result.
 */
public final class UserSearchRow {

    private final String username;
    private final String displayName;

    /**
     * Creates a user search row.
     * @param username the username
     * @param displayName the display name
     */
    public UserSearchRow(final String username, final String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }
}
