package interface_adapter.search_result;

import java.util.ArrayList;
import java.util.List;

import entity.Media;

/**
 * The state for Search Result View.
 */
public class SearchResultState {

    private List<Media> originalResults = new ArrayList<>();
    private List<Media> results = new ArrayList<>();
    private String filterError;

    /**
     * Returns the original unfiltered search results.
     */
    public List<Media> getOriginalResults() {
        return originalResults;
    }

    /**
     * Sets the original unfiltered search results.
     */
    public void setOriginalResults(List<Media> originalResults) {
        this.originalResults = originalResults;
    }

    /**
     * Returns current search results.
     */
    public List<Media> getResults() {
        return results;
    }

    /**
     * Sets the search results.
     */
    public void setResults(List<Media> results) {
        this.results = results;
    }

    /**
     * Returns the current filter error (not no result).
     */
    public String getFilterError() {
        return filterError;
    }

    /**
     * Sets the current filter error (not no result).
     */
    public void setFilterError(String filterError) {
        this.filterError = filterError;
    }
}
