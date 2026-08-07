package entity.recommendation;

import java.util.Collections;
import java.util.List;

/** Everything a sub-score needs to judge one candidate, beyond the candidate itself. */
public class ScoringContext {

    private final TasteProfile tasteProfile;
    private final List<Double> friendRatings;
    private final int currentYear;
    private final ScoringWeights weights;

    /** Creates a context for scoring a single candidate. */
    public ScoringContext(final TasteProfile tasteProfile, final List<Double> friendRatings,
                          final int currentYear, final ScoringWeights weights) {
        this.tasteProfile = tasteProfile;
        this.friendRatings = Collections.unmodifiableList(friendRatings);
        this.currentYear = currentYear;
        this.weights = weights;
    }

    /** Returns the user's taste profile. */
    public TasteProfile getTasteProfile() {
        return this.tasteProfile;
    }

    /** Returns the friends' ratings for the candidate being scored. */
    public List<Double> getFriendRatings() {
        return this.friendRatings;
    }

    /** Returns the year recency is measured against. */
    public int getCurrentYear() {
        return this.currentYear;
    }

    /** Returns the weighting in force. */
    public ScoringWeights getWeights() {
        return this.weights;
    }
}
