package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final String displayName;
    private final boolean useCaseFailed;

    public LoginOutputData(String username, String displayName, boolean useCaseFailed) {
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

}
