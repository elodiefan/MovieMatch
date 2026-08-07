package use_case.search;

import entity.Media;

/** Interface for accessing media data for search. */
public interface SearchMediaDataAccess
        extends Searchable<Media> {

    /** Fetches a single page of results for a keyword. */
    MediaPage searchPage(String keyword, int page);
}
