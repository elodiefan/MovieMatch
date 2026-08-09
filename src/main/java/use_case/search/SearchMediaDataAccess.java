package use_case.search;

import entity.Media;

/**
 * Interface for accessing media data for search.
 */
public interface SearchMediaDataAccess
        extends Searchable<Media> {

    /**
     * Fetches a single page of results for a keyword.
     * Fetching one page at a time is what keeps a search affordable. A broad
     * keyword can match ten thousand titles, so asking the source for
     * everything at once is not something a user can wait for.
     *
     * @param keyword the keyword
     * @param page the page
     * @return the search page
     */
    MediaPage searchPage(String keyword, int page);
}
