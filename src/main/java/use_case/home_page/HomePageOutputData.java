package use_case.home_page;

/**
 * Output Data for the Home Page Use Case.
 */

public class HomePageOutputData {

    private String username;
    private String displayName;
    private boolean useCaseFailed;

    public HomePageOutputData(String username, String displayName, boolean useCaseFailed) {
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
