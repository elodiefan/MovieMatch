package use_case.get_profile;

/**
 * Output Data for the Get Profile Use Case.
 */

public class GetProfileOutputData {

    private String username;
    private String displayName;
    private boolean useCaseFailed;

    public GetProfileOutputData(String username, String displayName, boolean useCaseFailed) {
        this.username = username;
        this.displayName = displayName;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
