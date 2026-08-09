package use_case.get_profile;

/**
 * Output Data for the Get Profile Use Case.
 */

public class GetProfileOutputData {

    private String username;
    private String displayName;
    private boolean isBlocked;
    private boolean useCaseFailed;

    public GetProfileOutputData(String username, String displayName, boolean isBlocked, boolean useCaseFailed) {
        this.username = username;
        this.displayName = displayName;
        this.isBlocked = isBlocked;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
