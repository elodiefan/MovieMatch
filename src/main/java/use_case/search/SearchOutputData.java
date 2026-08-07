package use_case.search;

import java.util.List;

import entity.Media;

/**
 * Output data for the Search Use Case.
 */
public class SearchOutputData {

    private final List<Media> results;

    public SearchOutputData(List<Media> results) {
        this.results = results;
    }

    /**
     * Returns the search results.
     *
     * @return list of media results
     */
    public List<Media> getResults() {
        return results;
    }
}
