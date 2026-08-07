package interface_adapter.reset_password;

/** What should happen once a password has been changed successfully. */
public interface PasswordResetCompletedHandler {

    /** Called after the new password has been saved. */
    void passwordResetCompleted(String username);
}