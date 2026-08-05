package use_case.search;

/**
 * Output boundary for the Search Use Case.
 */
public interface SearchOutputBoundary {

    /**
     * Prepares the success view after searching.
     *
     * @param outputData the search results
     */
    void prepareSuccessView(SearchOutputData outputData);

    /**
     * Remain in search page with a notification when failed.
     *
     * @param error the error message
     */
    void prepareFailView(String error);
}
