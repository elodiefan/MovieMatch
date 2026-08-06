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
     *
     * @return the original search results
     */
    public List<Media> getOriginalResults() {
        return originalResults;
    }

    /**
     * Sets the original unfiltered search results.
     *
     * @param originalResults the original search results
     */
    public void setOriginalResults(List<Media> originalResults) {
        this.originalResults = originalResults;
    }

    /**
     * Returns current search results.
     *
     * @return list of media results
     */
    public List<Media> getResults() {
        return results;
    }

    /**
     * Sets the search results.
     *
     * @param results the media results
     */
    public void setResults(List<Media> results) {
        this.results = results;
    }

    /**
     * Returns the current filter error (not no result).
     *
     * @return the filter error, or null when there is no error
     */
    public String getFilterError() {
        return filterError;
    }

    /**
     * Sets the current filter error (not no result).
     *
     * @param filterError the filter error
     */
    public void setFilterError(String filterError) {
        this.filterError = filterError;
    }
}
