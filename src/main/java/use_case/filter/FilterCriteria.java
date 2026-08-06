package use_case.filter;

import java.util.List;

/**
 * Contains the criteria used to filter media search results.
 */
public class FilterCriteria {

    private final List<String> languages;
    private final Double minimumRating;
    private final List<Integer> genreIds;
    private final Integer earliestYear;
    private final Integer latestYear;

    public FilterCriteria(
            List<String> languages,
            Double minimumRating,
            List<Integer> genreIds,
            Integer earliestYear,
            Integer latestYear) {
        this.languages = languages;
        this.minimumRating = minimumRating;
        this.genreIds = genreIds;
        this.earliestYear = earliestYear;
        this.latestYear = latestYear;
    }

    /**
     * Returns the selected languages.
     *
     * @return the selected language codes
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * Returns the minimum acceptable rating.
     *
     * @return the minimum rating, or null when not selected
     */
    public Double getMinimumRating() {
        return minimumRating;
    }

    /**
     * Returns the selected genre IDs.
     *
     * @return the selected genre IDs
     */
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    /**
     * Returns the earliest acceptable release year.
     *
     * @return the earliest year, or null when not selected
     */
    public Integer getEarliestYear() {
        return earliestYear;
    }

    /**
     * Returns the latest acceptable release year.
     *
     * @return the latest year, or null when not selected
     */
    public Integer getLatestYear() {
        return latestYear;
    }
}
