package use_case.signup;

/**
 * The output boundary for the Signup Use Case.
 */
public interface SignupOutputBoundary {

    /**
     * Prepares the success view for the Signup Use Case.
     */
    void prepareSuccessView(SignupOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the Login View.
     */
    void switchToLoginView();
}
