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
     * Kept so more pages of the same search can be requested.
     */
    private String keyword = "";
    private int nextPage = 1;
    private boolean moreAvailable;

    /**
     * How many results exist in total, not how many are loaded.
     */
    private int totalResults;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public void setMoreAvailable(boolean moreAvailable) {
        this.moreAvailable = moreAvailable;
    }

    /**
     * Returns the original unfiltered search results.
     *
     * @return the get original results
     */
    public List<Media> getOriginalResults() {
        return originalResults;
    }

    /**
     * Sets the original unfiltered search results.
     *
     * @param originalResults the original results
     */
    public void setOriginalResults(List<Media> originalResults) {
        this.originalResults = originalResults;
    }

    /**
     * Returns current search results.
     *
     * @return the get results
     */
    public List<Media> getResults() {
        return results;
    }

    /**
     * Sets the search results.
     *
     * @param results the results
     */
    public void setResults(List<Media> results) {
        this.results = results;
    }

    /**
     * Returns the current filter error (not no result).
     *
     * @return the get filter error
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
