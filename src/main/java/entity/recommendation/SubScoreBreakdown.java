package entity.recommendation;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The individual factor values behind one candidate's final score.
 * <p>
 * Kept alongside the total for two reasons. It lets the app explain a suggestion
 * in the user's own terms — "because you like Sci-Fi" — without recomputing
 * anything. And when a scoring test fails it shows which factor was wrong rather
 * than only that the total missed, which is the difference between a one-minute
 * fix and an afternoon.
 * <p>
 * Insertion order is preserved so breakdowns always read in the same order.
 */
public class SubScoreBreakdown {

    private final Map<String, Double> rawScores;
    private final Map<String, Double> weightedScores;

    /**
     * Creates a breakdown.
     *
     * @param rawScores each factor's own [0, 1] value, keyed by factor name
     * @param weightedScores each factor's value after its weight is applied
     */
    public SubScoreBreakdown(final Map<String, Double> rawScores,
                             final Map<String, Double> weightedScores) {
        this.rawScores = Collections.unmodifiableMap(new LinkedHashMap<>(rawScores));
        this.weightedScores = Collections.unmodifiableMap(new LinkedHashMap<>(weightedScores));
    }

    /**
     * Returns each factor's unweighted value.
     *
     * @return an unmodifiable map from factor name to its [0, 1] score
     */
    public Map<String, Double> getRawScores() {
        return this.rawScores;
    }

    /**
     * Returns each factor's contribution to the total.
     *
     * @return an unmodifiable map from factor name to its weighted score
     */
    public Map<String, Double> getWeightedScores() {
        return this.weightedScores;
    }

    /**
     * Returns the name of the factor that contributed most to the total.
     * <p>
     * This is what makes a suggestion explainable: the strongest contributor is
     * the honest answer to "why am I being shown this?".
     *
     * @return the highest-contributing factor's name, or an empty string if there
     *         are no factors
     */
    public String getStrongestFactor() {
        return this.weightedScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }
}
