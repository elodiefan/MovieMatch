package use_case.get_profile;

/**
 * The input data for the get profile use case.
 */

public class GetProfileInputData {

    private final String username;
    private final String displayName;

    public GetProfileInputData(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    String getUsername() {
        return username;
    }

    String getDisplayName() {
        return displayName;
    }
}
