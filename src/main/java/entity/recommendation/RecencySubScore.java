package entity.recommendation;

import entity.Media;

/** Scores a candidate on how recently it came out. */
public class RecencySubScore implements SubScore {

    private static final String NAME = "recency";
    private static final double OLDEST = 0.0;
    private static final double NEWEST = 1.0;

    /** Creates the recency factor. */
    public RecencySubScore() {
        // Stateless: everything needed arrives through scoreFor.
    }

    @Override
    public double scoreFor(final Media candidate, final ScoringContext context) {
        final int window = context.getWeights().getRecencyWindowYears();
        final int age = context.getCurrentYear() - candidate.getReleaseYear();
        // A title dated in the future would otherwise score above 1.0, so clamp
        // both ends rather than only flooring at zero.
        final double fresh = NEWEST - (double) age / window;
        return Math.max(OLDEST, Math.min(NEWEST, fresh));
    }

    @Override
    public double weightFrom(final ScoringWeights weights) {
        return weights.getRecency();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
