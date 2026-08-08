package use_case.search;

import java.util.ArrayList;
import java.util.List;

import entity.Media;

/**
 * One page of search results, and how many pages exist in total.
 */
public class MediaPage {

    private final List<Media> media;
    private final int totalPages;
    private final int totalResults;

    public MediaPage(List<Media> media, int totalPages, int totalResults) {
        this.media = new ArrayList<>(media);
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public List<Media> getMedia() {
        return new ArrayList<>(media);
    }

    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Returns how many results the source says exist for this keyword, across every page, not just the ones fetched so far.
     *
     * @return the get total results
     */
    public int getTotalResults() {
        return totalResults;
    }
}
