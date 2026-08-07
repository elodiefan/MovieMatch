package use_case.search;

import java.util.ArrayList;
import java.util.List;

import entity.Media;

/**
 * One page of search results, and how many pages exist in total.
 * <p>
 * Search sources hand back results a page at a time. Carrying the page count
 * alongside the results is what lets the use case decide how many pages to ask
 * for, rather than the data access deciding for it.
 */
public class MediaPage {

    private final List<Media> media;
    private final int totalPages;

    public MediaPage(List<Media> media, int totalPages) {
        this.media = new ArrayList<>(media);
        this.totalPages = totalPages;
    }

    public List<Media> getMedia() {
        return new ArrayList<>(media);
    }

    public int getTotalPages() {
        return totalPages;
    }
}
