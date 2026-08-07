package use_case.change_display_name;

/**
 * Output data for the change display name use case.
 */
public class ChangeDisplayNameOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public ChangeDisplayNameOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
