package use_case.logout;

/** The output boundary for the Login Use Case. */
public interface LogoutOutputBoundary {
    /** Prepares the success view for the Login Use Case. */
    void prepareSuccessView(LogoutOutputData outputData);

    /** Prepares the failure view for the Login Use Case. */
    void prepareFailView(String errorMessage);
}
