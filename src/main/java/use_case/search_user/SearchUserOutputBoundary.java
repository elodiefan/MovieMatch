package use_case.search_user;

/** The output boundary for the Search User Use Case. */
public interface SearchUserOutputBoundary {

    /** Prepares the success view after searching. */
    void prepareSuccessView(SearchUserOutputData outputData);

    /** Stays on the search page and shows why the search did not run. */
    void prepareFailView(String error);
}
