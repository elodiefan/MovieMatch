package entity.recommendation;

import entity.Media;

/**
 * Scores a candidate on how well it is regarded generally.
 * <p>
 * Acts as a tie-breaker between titles that match the user's taste equally well,
 * and as a safety net for a brand-new user whose taste profile is still empty:
 * with nothing to overlap against, popularity and recency are all that separate
 * the candidates.
 * <p>
 * TMDB rates on a 0-10 scale, so the value is divided by ten to normalise it.
 */
public class PopularitySubScore implements SubScore {

    /** TMDB's average rating runs from 0 to 10. */
    private static final double MAX_TMDB_RATING = 10.0;

    private static final String NAME = "popularity";
    private static final double LOWEST = 0.0;

    /**
     * Creates the popularity factor.
     */
    public PopularitySubScore() {
        // Stateless: everything needed arrives through scoreFor.
    }

    @Override
    public double scoreFor(final Media candidate, final ScoringContext context) {
        // Clamp rather than trust the source: an out-of-range rating from a
        // future data provider must not push a final score above 1.0.
        final double normalised = candidate.getAverageRating() / MAX_TMDB_RATING;
        return Math.max(LOWEST, Math.min(1.0, normalised));
    }

    @Override
    public double weightFrom(final ScoringWeights weights) {
        return weights.getPopularity();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
