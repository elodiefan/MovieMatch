package interface_adapter.reset_password;

/**
 * What should happen once a password has been changed successfully.
 * Declared here so {@link ResetPasswordPresenter} does not need to know which
 * screen comes next. Whatever wants to react â€” normally the screen the user
 * started from â€” implements this, and the dependency points inward to this
 * package rather than outward to another one.
 */
public interface PasswordResetCompletedHandler {

    /**
     * Called after the new password has been saved.
     * @param username the account whose password was changed
     */
    void passwordResetCompleted(String username);
}
