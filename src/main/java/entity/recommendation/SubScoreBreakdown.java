package entity.recommendation;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** The individual factor values behind one candidate's final score. */
public class SubScoreBreakdown {

    private final Map<String, Double> rawScores;
    private final Map<String, Double> weightedScores;

    /** Creates a breakdown. */
    public SubScoreBreakdown(final Map<String, Double> rawScores,
                             final Map<String, Double> weightedScores) {
        this.rawScores = Collections.unmodifiableMap(new LinkedHashMap<>(rawScores));
        this.weightedScores = Collections.unmodifiableMap(new LinkedHashMap<>(weightedScores));
    }

    /** Returns each factor's unweighted value. */
    public Map<String, Double> getRawScores() {
        return this.rawScores;
    }

    /** Returns each factor's contribution to the total. */
    public Map<String, Double> getWeightedScores() {
        return this.weightedScores;
    }

    /** Returns the name of the factor that contributed most to the total. */
    public String getStrongestFactor() {
        return this.weightedScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }
}
