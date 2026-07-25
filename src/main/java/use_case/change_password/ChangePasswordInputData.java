package use_case.change_password;

/**
 * The input data for the Change Password Use Case.
 */
public class ChangePasswordInputData {

    private final String password;
    private final String username;
    private final String securityAnswer;

    public ChangePasswordInputData(String password, String username, String securityAnswer) {
        this.password = password;
        this.username = username;
        this.securityAnswer = securityAnswer;
    }

    String getPassword() {
        return password;
    }

    String getUsername() {
        return username;
    }

    String getSecurityAnswer() {
        return securityAnswer;
    }

}
