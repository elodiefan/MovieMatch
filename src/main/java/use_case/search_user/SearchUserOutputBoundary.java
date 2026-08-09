package use_case.search_user;

/**
 * The output boundary for the Search User Use Case.
 */
public interface SearchUserOutputBoundary {

    /**
     * Prepares the success view after searching.
     * Finding nobody is still a success: the search ran and the answer was zero
     * users. Only a keyword we refused to search for counts as a failure.
     * @param outputData the users that matched
     */
    void prepareSuccessView(SearchUserOutputData outputData);

    /**
     * Stays on the search page and shows why the search did not run.
     * @param error the message to show
     */
    void prepareFailView(String error);
}
