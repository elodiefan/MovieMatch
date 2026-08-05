package use_case.get_security_question;

/**
 * The output boundary for the get security question use case.
 */

public interface GetSecurityQuestionOutputBoundary {
    /**
     * Switches to Delete Account View.
     * @param getSecurityQuestionOutputData output data for account use case
     */
    void switchToDeleteAccountView(GetSecurityQuestionOutputData getSecurityQuestionOutputData);
}
