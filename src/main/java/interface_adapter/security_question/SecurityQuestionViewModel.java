package interface_adapter.security_question;

import interface_adapter.ViewModel;

/**
 * View model for the Security Question view.
 * <p>
 * Follows the same shape as {@code LoginViewModel}: it names the view and holds
 * an initial {@link SecurityQuestionState}. The view registers as a listener and
 * repaints whenever the presenter calls {@code firePropertyChanged()}.
 */
public class SecurityQuestionViewModel extends ViewModel<SecurityQuestionState> {

    public SecurityQuestionViewModel() {
        super("security question");
        setState(new SecurityQuestionState());
    }
}
