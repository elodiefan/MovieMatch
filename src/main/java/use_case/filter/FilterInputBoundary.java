package use_case.filter;

/** Input boundary for filtering media search results. */
public interface FilterInputBoundary {

    /** Filters media using the provided criteria. */
    void execute(FilterInputData inputData);
}
