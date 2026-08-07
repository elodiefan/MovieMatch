package use_case.recommendation;

/** A request for recommendations. */
public class RecommendationInputData {

    /** How many suggestions the home page strip shows. */
    public static final int HOME_PAGE_LIMIT = 5;

    /** How many suggestions the dedicated view shows. */
    public static final int DETAILED_LIMIT = 10;

    private final String username;
    private final int limit;
    private final boolean groupByGenre;

    /** Creates a request. */
    public RecommendationInputData(final String username, final int limit,
                                   final boolean groupByGenre) {
        this.username = username;
        this.limit = limit;
        this.groupByGenre = groupByGenre;
    }

    /** Returns the user to recommend for. */
    public String getUsername() {
        return this.username;
    }

    /** Returns how many suggestions to return. */
    public int getLimit() {
        return this.limit;
    }

    /** Reports whether results should be grouped into genre sections. */
    public boolean isGroupByGenre() {
        return this.groupByGenre;
    }
}
