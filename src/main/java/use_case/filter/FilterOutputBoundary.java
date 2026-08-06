package use_case.filter;

/**
 * Output boundary for the Filter Use Case.
 */
public interface FilterOutputBoundary {

    /**
     * Prepares the view with the filtered results.
     *
     * @param outputData the filtered media results
     */
    void prepareSuccessView(FilterOutputData outputData);

    /**
     * Prepares the view when the filter criteria are invalid.
     *
     * @param error the error message
     */
    void prepareFailView(String error);
}
