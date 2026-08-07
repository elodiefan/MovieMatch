package interface_adapter.filter;

import java.util.List;

import entity.Media;
import use_case.filter.FilterCriteria;

/**
 * Request model for filter input collected by the search results view.
 */
public final class FilterRequestModel {
    private final List<Media> originalResults;
    private final FilterCriteria criteria;

    /**
     * Creates a request model for filtering search results.
     */
    public FilterRequestModel(final List<Media> originalResults,
                              final FilterCriteria criteria) {
        this.originalResults = originalResults;
        this.criteria = criteria;
    }

    /**
     * Returns the unfiltered search results.
     */
    public List<Media> getOriginalResults() {
        return originalResults;
    }

    /**
     * Returns the selected filter criteria.
     */
    public FilterCriteria getCriteria() {
        return criteria;
    }
}
