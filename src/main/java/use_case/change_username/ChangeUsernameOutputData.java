package use_case.change_username;

/**
 * Output data for the change display name use case.
 */
public class ChangeUsernameOutputData {

    private final String username;
    private final String newUsername;
    private final String displayName;

    public ChangeUsernameOutputData(String username, String newUsername, String displayName) {
        this.username = username;
        this.newUsername = newUsername;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public String getDisplayName() {
        return displayName;
    }
}
