package use_case.security_question;

/** Input boundary for the "Change Password via Security Question" use case. */
public interface SecurityQuestionInputBoundary {

    /** Looks up the given user and asks the presenter to display their security question. */
    void loadSecurityQuestion(SecurityQuestionInputData inputData);

    /** Verifies the submitted security answer for the given user. */
    void verifyAnswer(SecurityQuestionInputData inputData);
}
