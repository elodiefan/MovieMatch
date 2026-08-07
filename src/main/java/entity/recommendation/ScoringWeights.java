package entity.recommendation;

/** How much each factor counts toward a candidate's final score. */
public class ScoringWeights {

    /** Weight of genre overlap in the default weighting. */
    public static final double DEFAULT_GENRE = 0.40;

    /** Weight of cast and crew overlap in the default weighting. */
    public static final double DEFAULT_CAST = 0.20;

    /** Weight of TMDB popularity in the default weighting. */
    public static final double DEFAULT_POPULARITY = 0.15;

    /** Weight of friends' ratings in the default weighting. */
    public static final double DEFAULT_FRIEND = 0.15;

    /** Weight of how recently the title came out, in the default weighting. */
    public static final double DEFAULT_RECENCY = 0.10;

    /** How many years it takes a title to decay to a recency score of zero. */
    public static final int DEFAULT_RECENCY_WINDOW_YEARS = 15;

    /** Weights must total this, so that scores stay within [0, 1]. */
    private static final double REQUIRED_TOTAL = 1.0;

    /** Floating-point slack allowed when checking the total. */
    private static final double TOLERANCE = 1.0E-9;

    private final double genre;
    private final double cast;
    private final double popularity;
    private final double friend;
    private final double recency;
    private final int recencyWindowYears;

    /** Creates a weighting. */
    public ScoringWeights(final double genre, final double cast, final double popularity,
                          final double friend, final double recency, final int recencyWindowYears) {
        final double total = genre + cast + popularity + friend + recency;
        if (Math.abs(total - REQUIRED_TOTAL) > TOLERANCE) {
            throw new IllegalArgumentException(
                    "Recommendation weights must sum to 1.0 but summed to " + total);
        }
        this.genre = genre;
        this.cast = cast;
        this.popularity = popularity;
        this.friend = friend;
        this.recency = recency;
        this.recencyWindowYears = recencyWindowYears;
    }

    /** Returns the weighting described in the algorithm document. */
    public static ScoringWeights createDefault() {
        return new ScoringWeights(DEFAULT_GENRE, DEFAULT_CAST, DEFAULT_POPULARITY,
                DEFAULT_FRIEND, DEFAULT_RECENCY, DEFAULT_RECENCY_WINDOW_YEARS);
    }

    /** Returns the weight of the genre overlap score. */
    public double getGenre() {
        return this.genre;
    }

    /** Returns the weight of the cast overlap score. */
    public double getCast() {
        return this.cast;
    }

    /** Returns the weight of the popularity score. */
    public double getPopularity() {
        return this.popularity;
    }

    /** Returns the weight of the friends' rating score. */
    public double getFriend() {
        return this.friend;
    }

    /** Returns the weight of the recency score. */
    public double getRecency() {
        return this.recency;
    }

    /** Returns how many years it takes recency to decay to zero. */
    public int getRecencyWindowYears() {
        return this.recencyWindowYears;
    }
}
