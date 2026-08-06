package use_case.search;

/**
 * Input boundary for searching media.
 */
public interface SearchInputBoundary {

    /**
     * Searches media using the given keyword.
     * @param inputData the search keyword
     */
    void execute(SearchInputData inputData);
}
