package interface_adapter.search_result;

import java.util.ArrayList;
import java.util.List;

import entity.Media;

/**
 * The state for Search Result View.
 */
public class SearchResultState {

    private List<Media> results = new ArrayList<>();

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
}
