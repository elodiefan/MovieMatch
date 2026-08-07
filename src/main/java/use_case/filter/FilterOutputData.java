package use_case.filter;

import java.util.List;

import entity.Media;

/**
 * Output data for the Filter Use Case.
 */
public class FilterOutputData {

    private final List<Media> filteredResults;

    public FilterOutputData(List<Media> filteredResults) {
        this.filteredResults = filteredResults;
    }

    /**
     * Returns the filtered media results.
     */
    public List<Media> getFilteredResults() {
        return filteredResults;
    }
}
