package use_case.security_question;

/**
 * Input boundary for the "Change Password via Security Question" use case.
 * <p>
 * The controller talks to the interactor only through this interface, so the
 * interface_adapter layer never depends on the concrete interactor. There are
 * two steps in this use case:
 * <ol>
 *     <li>{@link #loadSecurityQuestion} — look up the user and show their
 *     security question so they know what to answer;</li>
 *     <li>{@link #verifyAnswer} — check the answer, count failed tries, and
 *     lock the account after too many wrong attempts.</li>
 * </ol>
 */
public interface SecurityQuestionInputBoundary {

    /**
     * Looks up the given user and asks the presenter to display their
     * security question. Fails if the account does not exist.
     * @param inputData holds the username (the answer field is ignored here)
     */
    void loadSecurityQuestion(SecurityQuestionInputData inputData);

    /**
     * Verifies the submitted security answer for the given user.
     * Handles wrong answers, remaining attempts, and lock-out.
     * @param inputData holds the username and the attempted answer
     */
    void verifyAnswer(SecurityQuestionInputData inputData);
}
