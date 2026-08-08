package interface_adapter.reset_password;

import interface_adapter.StateModel;

/**
 * View model for the Reset Password view. Its view name "reset password" is
 * the target the security-question presenter switches to on success.
 */
public class ResetPasswordViewModel extends StateModel<ResetPasswordState> {

    public static final String VIEW_NAME = "reset password";
    public static final String BACK_BUTTON = "back to login";

    public ResetPasswordViewModel() {
        super(VIEW_NAME);
        setState(new ResetPasswordState());
    }
}
