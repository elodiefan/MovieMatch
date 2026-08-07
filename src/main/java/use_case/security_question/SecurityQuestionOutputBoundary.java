package use_case.security_question;

/**
 * Output boundary for the Security Question use case.
 * <p>
 * The interactor reports results back through this interface; the presenter
 * implements it and updates the view model. Three distinct outcomes are
 * separated so the presenter can react differently to each.
 */
public interface SecurityQuestionOutputBoundary {

    /**
     * Show the user's security question so they can answer it.
     */
    void presentSecurityQuestion(SecurityQuestionOutputData outputData);

    /**
     * The answer was correct — identity confirmed. The presenter should now
     * open the change-password window.
     */
    void prepareSuccessView(SecurityQuestionOutputData outputData);

    /**
     * The step failed: wrong answer (with attempts remaining), account locked
     * out, or no such account. The presenter shows the appropriate message.
     */
    void prepareFailView(SecurityQuestionOutputData outputData);
}
