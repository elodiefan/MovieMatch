package use_case.reset_password;

/**
 * Input data for the Reset Password use case: which user, and the new password typed twice (so the interactor can confirm the two entries match).
 */
public class ResetPasswordInputData {

    private final String username;
    private final String newPassword;
    private final String confirmPassword;

    public ResetPasswordInputData(String username, String newPassword, String confirmPassword) {
        this.username = username;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    String getUsername() {
        return username;
    }

    String getNewPassword() {
        return newPassword;
    }

    String getConfirmPassword() {
        return confirmPassword;
    }
}
