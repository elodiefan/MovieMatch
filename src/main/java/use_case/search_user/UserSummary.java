package use_case.search_user;

/** The little bit of a user that search results are allowed to show. */
public class UserSummary {

    private final String username;
    private final String displayName;

    public UserSummary(String username, String displayName) {
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
