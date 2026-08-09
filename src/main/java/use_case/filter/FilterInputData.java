package use_case.filter;

import java.util.List;

import use_case.search.MediaResultData;

/**
 * Input data for the Filter Use Case.
 */
public class FilterInputData {

    private final List<MediaResultData> originalResults;
    private final FilterCriteria criteria;

    public FilterInputData(
            List<MediaResultData> originalResults,
            FilterCriteria criteria) {
        this.originalResults = originalResults;
        this.criteria = criteria;
    }

    /**
     * Returns the original unfiltered search results.
     *
     * @return the original search results
     */
    public List<MediaResultData> getOriginalResults() {
        return originalResults;
    }

    /**
     * Returns the selected filter criteria.
     *
     * @return the filter criteria
     */
    public FilterCriteria getCriteria() {
        return criteria;
    }
}
