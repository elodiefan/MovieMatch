package interface_adapter.reset_password;

/**
 * State backing the Reset Password view.
 *
 * username is seeded by the security-question step (the account whose
 * identity was just confirmed); the two password fields are typed by the user.
 */
public class ResetPasswordState {

    private String username = "";
    private String newPassword = "";
    private String confirmPassword = "";
    private String message = "";
    private String error = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
