package use_case.home_page;

/**
 * Output Data for the Home Page Use Case.
 */

public class HomePageOutputData {

    private String username;
    private boolean useCaseFailed;

    public HomePageOutputData(String username, boolean useCaseFailed) {
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
