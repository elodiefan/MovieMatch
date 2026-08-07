package use_case.recommendation;

/**
 * A request for recommendations.
 * <p>
 * Carries what differs between the two screens that ask for them: the home page
 * wants a handful, ungrouped; the dedicated view wants more, arranged into
 * genre sections. Both go through the same use case.
 */
public class RecommendationInputData {

    /** How many suggestions the home page strip shows. */
    public static final int HOME_PAGE_LIMIT = 5;

    /** How many suggestions the dedicated view shows. */
    public static final int DETAILED_LIMIT = 10;

    private final String username;
    private final int limit;
    private final boolean groupByGenre;

    /**
     * Creates a request.
     *
     * @param username the user to recommend for
     * @param limit how many suggestions to return
     * @param groupByGenre whether to arrange the results into genre sections
     */
    public RecommendationInputData(final String username, final int limit,
                                   final boolean groupByGenre) {
        this.username = username;
        this.limit = limit;
        this.groupByGenre = groupByGenre;
    }

    /**
     * Returns the user to recommend for.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns how many suggestions to return.
     *
     * @return the limit
     */
    public int getLimit() {
        return this.limit;
    }

    /**
     * Reports whether results should be grouped into genre sections.
     *
     * @return true if the caller wants sections
     */
    public boolean isGroupByGenre() {
        return this.groupByGenre;
    }
}
