package use_case.login;

/** The output boundary for the Login Use Case. */
public interface LoginOutputBoundary {
    /** Prepares the success view for the Login Use Case. */
    void prepareSuccessView(LoginOutputData outputData);

    /** Prepares the failure view for the Login Use Case. */
    void prepareFailView(String errorMessage);

    /** Executes the switch to reviews view use case. */
    void switchToSignUpView();

    /** Executes the switch to reviews view use case. */
    void switchToHomePageView();
}
