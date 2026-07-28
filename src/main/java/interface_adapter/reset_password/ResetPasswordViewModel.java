package interface_adapter.reset_password;

import interface_adapter.ViewModel;

/**
 * View model for the Reset Password view. Its view name — "reset password" — is
 * the target the security-question presenter switches to on success.
 */
public class ResetPasswordViewModel extends ViewModel<ResetPasswordState> {

    public ResetPasswordViewModel() {
        super("reset password");
        setState(new ResetPasswordState());
    }
}
