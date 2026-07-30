package use_case.home_page;

/**
 * The Input Data for the Home Page Use Case.
 */
public class HomePageInputData {

    private final String username;
    private final String password;

    public HomePageInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}
