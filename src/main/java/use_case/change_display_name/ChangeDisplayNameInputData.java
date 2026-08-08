package use_case.change_display_name;

/**
 * Input data for the change display name use case.
 */
public class ChangeDisplayNameInputData {

    private final String username;
    private final String oldDisplayName;
    private final String newDisplayName;

    public ChangeDisplayNameInputData(String username, String oldDisplayName,
                                      String newDisplayName) {
        this.username = username;
        this.oldDisplayName = oldDisplayName;
        this.newDisplayName = newDisplayName;
    }

    String getUsername() {
        return username;
    }

    String getOldDisplayName() {
        return oldDisplayName;
    }

    String getNewDisplayName() {
        return newDisplayName;
    }
}
