package entity.recommendation;

import entity.Media;

/**
 * Scores a candidate on how many of its genres the user already enjoys.
 * <p>
 * The heaviest factor in the algorithm: someone who rates science fiction highly
 * should mostly be shown science fiction. Computed as the share of the
 * candidate's own genres that appear in the user's taste profile, so a candidate
 * squarely inside the user's taste scores 1.0 and one sharing nothing scores 0.
 */
public class GenreOverlapSubScore implements SubScore {

    private static final String NAME = "genre";

    /**
     * Creates the genre overlap factor.
     */
    public GenreOverlapSubScore() {
        // Stateless: everything needed arrives through scoreFor.
    }

    @Override
    public double scoreFor(final Media candidate, final ScoringContext context) {
        return SetOverlapCalculator.overlapRatio(
                candidate.getGenres(), context.getTasteProfile().getGenres());
    }

    @Override
    public double weightFrom(final ScoringWeights weights) {
        return weights.getGenre();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
