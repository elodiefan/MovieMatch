package use_case.signup;

/**
 * The Input Data for the Signup Use Case.
 */
public class SignupInputData {

    private final String username;
    private final String displayName;
    private final String password;
    private final String repeatPassword;
    private final String securityAnswer;

    public SignupInputData(String username, String displayName, String password, String repeatPassword, String securityAnswer) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.securityAnswer = securityAnswer;
    }

    String getUsername() {
        return username;
    }

    String getDisplayName() {
        return displayName;
    }

    String getPassword() {
        return password;
    }

    String getRepeatPassword() {
        return repeatPassword;
    }

    String getSecurityAnswer() {
        return securityAnswer;
    }
}
