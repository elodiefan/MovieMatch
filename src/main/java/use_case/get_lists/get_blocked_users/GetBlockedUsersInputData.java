package use_case.get_lists.get_blocked_users;

/**
 * Input data for the get blocked users use case.
 */
public class GetBlockedUsersInputData {

    private final String username;
    private final String displayName;

    public GetBlockedUsersInputData(String username, String displayName) {
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
