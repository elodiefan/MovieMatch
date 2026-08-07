package use_case.security_question;

/** Output boundary for the Security Question use case. */
public interface SecurityQuestionOutputBoundary {

    /** Show the user's security question so they can answer it. */
    void presentSecurityQuestion(SecurityQuestionOutputData outputData);

    /** The answer was correct — identity confirmed. */
    void prepareSuccessView(SecurityQuestionOutputData outputData);

    /** The step failed: wrong answer (with attempts remaining), account locked out, or no such account. */
    void prepareFailView(SecurityQuestionOutputData outputData);
}
