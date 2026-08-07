package entity.recommendation;

import entity.Media;

/**
 * Scores a candidate on how much of its cast and crew the user already enjoys.
 * <p>
 * Catches the taste that genre alone misses — following a particular director or
 * actor across films of different kinds. Computed as the share of the
 * candidate's own cast that appears in the user's taste profile.
 * <p>
 * Titles with no cast listed score 0 rather than failing, which matters because
 * cast data is often missing for older or more obscure entries.
 */
public class CastOverlapSubScore implements SubScore {

    private static final String NAME = "cast";

    /**
     * Creates the cast overlap factor.
     */
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
