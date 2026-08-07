package use_case.search_user;

/**
 * The little bit of a user that search results are allowed to show.
 * <p>
 * A {@link entity.User} also carries the password, the security question and its
 * answer. Search results are handed to a presenter and then drawn on screen, so
 * passing whole users outward would put credentials in the view layer for no
 * reason. The interactor maps each result down to this before anything leaves
 * the use case.
 */
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
