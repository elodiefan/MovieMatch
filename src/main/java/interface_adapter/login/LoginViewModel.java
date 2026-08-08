package interface_adapter.login;

import interface_adapter.StateModel;

/**
 * The View Model for the Login View.
 */
public class LoginViewModel extends StateModel<LoginState> {
    public static final String VIEW_NAME = "log in";
    public static final String USERNAME_BUTTON = "Username";
    public static final String PASSWORD_BUTTON = "Password";
    public static final String LOGIN_BUTTON = "Log In";
    public static final String SIGN_UP_BUTTON = "Sign Up";
    public static final String FORGOT_PASSWORD_BUTTON = "Forgot Password";

    public LoginViewModel() {
        super(VIEW_NAME);
        setState(new LoginState());
    }

}
