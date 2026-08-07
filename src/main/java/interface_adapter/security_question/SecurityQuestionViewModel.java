package interface_adapter.security_question;

import interface_adapter.StateModel;

/** View model for the Security Question view. */
public class SecurityQuestionViewModel extends StateModel<SecurityQuestionState> {

    public static final String VIEW_NAME = "security question";
    public static final String BACK_BUTTON = "back to login";

    public SecurityQuestionViewModel() {
        super(VIEW_NAME);
        setState(new SecurityQuestionState());
    }
}
