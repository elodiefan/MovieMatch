package entity.recommendation;

import entity.Media;

/** Scores a candidate on how well it is regarded generally. */
public class PopularitySubScore implements SubScore {

    /** TMDB's average rating runs from 0 to 10. */
    private static final double MAX_TMDB_RATING = 10.0;

    private static final String NAME = "popularity";
    private static final double LOWEST = 0.0;

    /** Creates the popularity factor. */
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
