package use_case.change_display_name;

/**
 * Input data for the change display name use case.
 */
public class ChangeDisplayNameInputData {

    private final String username;
    private final String newDisplayName;

    public ChangeDisplayNameInputData(String username, String newDisplayName) {
        this.username = username;
        this.newDisplayName = newDisplayName;
    }

    String getUsername() {
        return username;
    }

    String getNewDisplayName() {
        return newDisplayName;
    }
}
