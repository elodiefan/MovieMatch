package use_case.home_page;

/** The Input Data for the Home Page Use Case. */
public class HomePageInputData {

    private final String username;
    private final String displayName;

    public HomePageInputData(String username, String displayName) {
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
