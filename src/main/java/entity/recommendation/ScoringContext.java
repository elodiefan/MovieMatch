package entity.recommendation;

import java.util.Collections;
import java.util.List;

/**
 * Everything a sub-score needs to judge one candidate, beyond the candidate itself.
 *
 * Each {@link SubScore} needs different surrounding information — genre overlap
 * needs the user's taste profile, the friends' score needs their ratings, recency
 * needs the current year. Bundling them here keeps the {@code SubScore} interface
 * to a single shape, so new factors can be added without changing every existing
 * scorer's signature.
 *
 * A context describes one candidate, because friends' ratings differ per title.
 */
public class ScoringContext {

    private final TasteProfile tasteProfile;
    private final List<Double> friendRatings;
    private final int currentYear;
    private final ScoringWeights weights;

    /**
     * Creates a context for scoring a single candidate.
     *
     * @param tasteProfile the genres and cast the user is known to like
     * @param friendRatings the ratings this user's friends gave this candidate,
     *                      on a 1-5 scale; empty when no friend has rated it
     * @param currentYear the year to measure recency against, injected rather than
     *                    read from the clock so results stay reproducible in tests
     * @param weights how much each factor counts
     */
    public ScoringContext(final TasteProfile tasteProfile, final List<Double> friendRatings,
                          final int currentYear, final ScoringWeights weights) {
        this.tasteProfile = tasteProfile;
        this.friendRatings = Collections.unmodifiableList(friendRatings);
        this.currentYear = currentYear;
        this.weights = weights;
    }

    /**
     * Returns the user's taste profile.
     *
     * @return the taste profile
     */
    public TasteProfile getTasteProfile() {
        return this.tasteProfile;
    }

    /**
     * Returns the friends' ratings for the candidate being scored.
     *
     * @return an unmodifiable list of ratings on a 1-5 scale, possibly empty
     */
    public List<Double> getFriendRatings() {
        return this.friendRatings;
    }

    /**
     * Returns the year recency is measured against.
     *
     * @return the current year
     */
    public int getCurrentYear() {
        return this.currentYear;
    }

    /**
     * Returns the weighting in force.
     *
     * @return the scoring weights
     */
    public ScoringWeights getWeights() {
        return this.weights;
    }
}
