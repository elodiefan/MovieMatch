package use_case.search;

import java.util.ArrayList;
import java.util.List;

import entity.Media;

/**
 * One page of search results, and how many pages exist in total.
 *
 * Search sources hand back results a page at a time. Carrying the page count
 * alongside the results is what lets the use case decide how many pages to ask
 * for, rather than the data access deciding for it.
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
     * Returns how many results the source says exist for this keyword, across
     * every page, not just the ones fetched so far.
     */
    public int getTotalResults() {
        return totalResults;
    }
}
