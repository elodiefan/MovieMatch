package use_case.get_blocked_users;

/**
 * Output Data for the Blocked Users View Use Case.
 */
public class GetBlockedUsersOutputData {
    
    private final String username;
    private final String displayName;
    private final String blockedUsers;

    public GetBlockedUsersOutputData(String username, String displayName, String blockedUsers) {
        this.username = username;
        this.displayName = displayName;
        this.blockedUsers = blockedUsers;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBlockedUsers() {
        return blockedUsers;
    }
}
