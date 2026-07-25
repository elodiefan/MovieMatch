package interface_adapter.signup;

/**
 * View model for the Signup View.
 */
public class SignupViewModel {

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
    public static final String[] SECURITY_QUESTION_OPTIONS = {
            "What was the name of your first pet?",
            "What city were you born in?",
            "What was the name of your first school?",
            "What is your favourite movie?",
            "What is your favourite TV show?"
    };

    private SignupState state = new SignupState();

    /**
     * Returns the current signup state.
     *
     * @return the current signup state
     */
    public SignupState getState() {
        return state;
    }

    /**
     * Updates the current signup state.
     *
     * @param state the new signup state
     */
    public void setState(SignupState state) {
        this.state = state;
    }

    /**
     * Returns the name used to identify the signup view.
     *
     * @return the signup view name
     */
    public String getViewName() {
        return VIEW_NAME;
    }
}
