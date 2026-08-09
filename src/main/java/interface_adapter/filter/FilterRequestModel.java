package interface_adapter.filter;

import java.util.List;

import interface_adapter.search_result.SearchResultRow;

/**
 * Request model for filter input collected by the search results view.
 */
public final class FilterRequestModel {
    private final List<SearchResultRow> originalResults;
    private final List<String> languages;
    private final Double minimumRating;
    private final List<Integer> genreIds;
    private final Integer earliestYear;
    private final Integer latestYear;

    /**
     * Creates a request model for filtering search results.
     * @param originalResults the unfiltered search results
     * @param languages the selected language codes
     * @param minimumRating the minimum rating
     * @param genreIds the selected genre ids
     * @param earliestYear the earliest release year
     * @param latestYear the latest release year
     */
    public FilterRequestModel(final List<SearchResultRow> originalResults,
                              final List<String> languages,
                              final Double minimumRating,
                              final List<Integer> genreIds,
                              final Integer earliestYear,
                              final Integer latestYear) {
        this.originalResults = originalResults;
        this.languages = languages;
        this.minimumRating = minimumRating;
        this.genreIds = genreIds;
        this.earliestYear = earliestYear;
        this.latestYear = latestYear;
    }

    /**
     * Returns the unfiltered search results.
     * @return the unfiltered search results
     */
    public List<SearchResultRow> getOriginalResults() {
        return originalResults;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Double getMinimumRating() {
        return minimumRating;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public Integer getEarliestYear() {
        return earliestYear;
    }

    public Integer getLatestYear() {
        return latestYear;
    }
}
