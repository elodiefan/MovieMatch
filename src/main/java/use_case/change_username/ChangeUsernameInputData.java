package use_case.change_username;

/**
 * Input data for the change username use case.
 */
public class ChangeUsernameInputData {

    private final String username;
    private final String newUsername;
    private final String displayName;

    public ChangeUsernameInputData(String username, String newUsername, String displayName) {
        this.username = username;
        this.newUsername = newUsername;
        this.displayName = displayName;
    }

    String getUsername() {
        return username;
    }

    String getNewUsername() {
        return newUsername;
    }

    String getDisplayName() {
        return displayName;
    }
}
