package use_case.filter;

import java.util.List;

import entity.Media;

/** Input data for the Filter Use Case. */
public class FilterInputData {

    private final List<Media> originalResults;
    private final FilterCriteria criteria;

    public FilterInputData(
            List<Media> originalResults,
            FilterCriteria criteria) {
        this.originalResults = originalResults;
        this.criteria = criteria;
    }

    /** Returns the original unfiltered search results. */
    public List<Media> getOriginalResults() {
        return originalResults;
    }

    /** Returns the selected filter criteria. */
    public FilterCriteria getCriteria() {
        return criteria;
    }
}
