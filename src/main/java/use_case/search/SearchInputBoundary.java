package use_case.search;

/**
 * Input boundary for searching media.
 */
public interface SearchInputBoundary {

    /**
     * Searches media using the given keyword.
     */
    void execute(SearchInputData inputData);
}
