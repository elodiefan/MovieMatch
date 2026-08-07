package use_case.search_user;

/**
 * The output boundary for the Search User Use Case.
 */
public interface SearchUserOutputBoundary {

    /**
     * Prepares the success view after searching.
     * <p>
     * Finding nobody is still a success: the search ran and the answer was zero
     * users. Only a keyword we refused to search for counts as a failure.
     */
    void prepareSuccessView(SearchUserOutputData outputData);

    /**
     * Stays on the search page and shows why the search did not run.
     */
    void prepareFailView(String error);
}
