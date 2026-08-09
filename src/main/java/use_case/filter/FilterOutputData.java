package use_case.filter;

import java.util.List;

import use_case.search.MediaResultData;

/**
 * Output data for the Filter Use Case.
 */
public class FilterOutputData {

    private final List<MediaResultData> filteredResults;

    public FilterOutputData(List<MediaResultData> filteredResults) {
        this.filteredResults = filteredResults;
    }

    /**
     * Returns the filtered media results.
     *
     * @return the filtered media results
     */
    public List<MediaResultData> getFilteredResults() {
        return filteredResults;
    }
}
