package interface_adapter.security_question;

import use_case.security_question.SecurityQuestionInputBoundary;
import use_case.security_question.SecurityQuestionInputData;

/**
 * Controller for the Security Question use case.
 *
 * Turns raw view input (plain strings) into use-case input data and forwards it
 * to the interactor through the input boundary. It holds no business logic.
 */
public class SecurityQuestionController {

    private final SecurityQuestionInputBoundary interactor;

    public SecurityQuestionController(SecurityQuestionInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Ask the use case to load and display this user's security question.
     */
    public void loadQuestion(String username) {
        // No answer yet at this stage, so pass an empty string.
        interactor.loadSecurityQuestion(new SecurityQuestionInputData(username, ""));
    }

    /**
     * Ask the use case to verify the submitted security answer.
     */
    public void verify(String username, String answer) {
        interactor.verifyAnswer(new SecurityQuestionInputData(username, answer));
    }
}
