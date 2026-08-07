package use_case.change_username;

/**
 * Output data for the change display name use case.
 */
public class ChangeUsernameOutputData {

    private final String username;
    private final String newUsername;

    public ChangeUsernameOutputData(String username, String newUsername) {
        this.username = username;
        this.newUsername = newUsername;
    }

    public String getUsername() {
        return username;
    }

    public String getNewUsername() {
        return newUsername;
    }
}
