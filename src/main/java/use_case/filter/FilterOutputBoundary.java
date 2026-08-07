package use_case.filter;

/**
 * Output boundary for the Filter Use Case.
 */
public interface FilterOutputBoundary {

    /**
     * Prepares the view with the filtered results.
     */
    void prepareSuccessView(FilterOutputData outputData);

    /**
     * Prepares the view when the filter criteria are invalid.
     */
    void prepareFailView(String error);
}
