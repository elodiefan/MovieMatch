package use_case.search;

/**
 * The Input Boundary for the Search Use Case.
 */
public interface SearchInputBoundary {

    /**
     * Runs a fresh search, replacing anything already on screen.
     *
     * @param searchInputData the search input data
     */
    void execute(SearchInputData searchInputData);

    /**
     * Fetches the next block of pages for a search already run, to be added to
     * the results the user is looking at.
     *
     * @param searchInputData the search input data
     */
    void loadMore(SearchInputData searchInputData);
}
