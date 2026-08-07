package use_case.search;

/** Output boundary for the Search Use Case. */
public interface SearchOutputBoundary {

    /** Prepares the success view after searching. */
    void prepareSuccessView(SearchOutputData outputData);

    /** Remain in search page with a notification when failed. */
    void prepareFailView(String error);
}
