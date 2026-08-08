package interface_adapter.signup;

import interface_adapter.StateModel;

/**
 * View model for the Signup View.
 */
public class SignupViewModel extends StateModel<SignupState> {

    public static final String VIEW_NAME = "sign up";
    public static final String TITLE_LABEL = "Sign Up";
    public static final String USERNAME_LABEL = "Username";
    public static final String DISPLAY_NAME_LABEL = "Display name";
    public static final String PASSWORD_LABEL = "Password";
    public static final String REPEAT_PASSWORD_LABEL = "Repeat password";
    public static final String SECURITY_QUESTION_LABEL = "Security question";
    public static final String SECURITY_ANSWER_LABEL = "Security answer";
    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";
    public static final String TO_LOGIN_BUTTON_LABEL = "Go to login";
    private static final String[] SECURITY_QUESTION_OPTIONS = {
        "What was the name of your first pet?",
        "What city were you born in?",
        "What was the name of your first school?",
        "What is your favourite movie?",
        "What is your favourite TV show?",
    };

    /**
     * Creates a signup view model.
     */
    public SignupViewModel() {
        super(VIEW_NAME);
        setState(new SignupState());
    }

    /**
     * Returns the available security question options.
     *
     * @return a copy of the security question options
     */
    public String[] getSecurityQuestionOptions() {
        return SECURITY_QUESTION_OPTIONS.clone();
    }
}
