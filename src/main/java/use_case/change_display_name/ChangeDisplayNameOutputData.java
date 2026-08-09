package use_case.change_display_name;

/**
 * Output data for the change display name use case.
 */
public class ChangeDisplayNameOutputData {

    private final String username;
    private final String oldDisplayName;
    private final String newDisplayName;

    public ChangeDisplayNameOutputData(String username, String oldDisplayName,
                                       String newDisplayName) {
        this.username = username;
        this.oldDisplayName = oldDisplayName;
        this.newDisplayName = newDisplayName;
    }

    public String getUsername() {
        return username;
    }

    public String getOldDisplayName() {
        return oldDisplayName;
    }

    public String getNewDisplayName() {
        return newDisplayName;
    }
}
