package interface_adapter.security_question;

import interface_adapter.StateModel;

/**
 * View model for the Security Question view.
 * <p>
 * Follows the same shape as {@code LoginViewModel}: it names the view and holds
 * an initial {@link SecurityQuestionState}. The view registers as a listener and
 * repaints whenever the presenter calls {@code firePropertyChanged()}.
 */
public class SecurityQuestionViewModel extends StateModel<SecurityQuestionState> {

    public static final String VIEW_NAME = "security question";
    public static final String BACK_BUTTON = "back to login";

    public SecurityQuestionViewModel() {
        super(VIEW_NAME);
        setState(new SecurityQuestionState());
    }
}
