package entity.recommendation;

import entity.Media;

/** Scores a candidate on how much of its cast and crew the user already enjoys. */
public class CastOverlapSubScore implements SubScore {

    private static final String NAME = "cast";

    /** Creates the cast overlap factor. */
    public CastOverlapSubScore() {
        // Stateless: everything needed arrives through scoreFor.
    }

    @Override
    public double scoreFor(final Media candidate, final ScoringContext context) {
        return SetOverlapCalculator.overlapRatio(
                candidate.getCast(), context.getTasteProfile().getCast());
    }

    @Override
    public double weightFrom(final ScoringWeights weights) {
        return weights.getCast();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
