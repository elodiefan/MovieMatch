package use_case.search;

import java.util.List;

/**
 * Output data for the Search Use Case.
 */
public class SearchOutputData {

    private final List<MediaResultData> results;
    private final String keyword;
    private final int nextPage;
    private final boolean moreAvailable;
    private final boolean appending;
    private final int totalResults;

    public SearchOutputData(List<MediaResultData> results, String keyword, int nextPage,
                            boolean moreAvailable, boolean appending, int totalResults) {
        this.results = results;
        this.keyword = keyword;
        this.nextPage = nextPage;
        this.moreAvailable = moreAvailable;
        this.appending = appending;
        this.totalResults = totalResults;
    }

    /**
     * Returns how many results exist in total, not how many have been fetched.
     *
     * @return the get total results
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * Returns the search results.
     *
     * @return the get results
     */
    public List<MediaResultData> getResults() {
        return results;
    }

    /**
     * Returns the keyword these results are for, so more can be requested.
     *
     * @return the get keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Returns the page a further request should start from.
     *
     * @return the get next page
     */
    public int getNextPage() {
        return nextPage;
    }

    /**
     * Returns whether the source has more pages left.
     *
     * @return the is more available
     */
    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    /**
     * Returns whether these results extend the previous ones rather than
     * replacing them.
     *
     * @return the is appending
     */
    public boolean isAppending() {
        return appending;
    }
}
