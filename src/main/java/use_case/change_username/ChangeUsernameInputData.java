package use_case.change_username;

/**
 * Input data for the change username use case.
 */
public class ChangeUsernameInputData {

    private final String username;
    private final String newUsername;

    public ChangeUsernameInputData(String username, String newUsername) {
        this.username = username;
        this.newUsername = newUsername;
    }

    String getUsername() {
        return username;
    }

    String getNewUsername() {
        return newUsername;
    }
}
