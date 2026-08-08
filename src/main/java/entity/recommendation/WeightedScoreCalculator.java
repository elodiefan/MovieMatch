package entity.recommendation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.Media;

/**
 * Combines the individual factors into one score for a candidate.
 */
public class WeightedScoreCalculator {

    private final List<SubScore> subScores;

    /**
     * Creates a calculator over the given factors.
     *
     * @param subScores the sub scores
     */
    public WeightedScoreCalculator(final List<SubScore> subScores) {
        this.subScores = new ArrayList<>(subScores);
    }

    /**
     * Creates a calculator over the five factors described in the algorithm document.
     *
     * @return the create default
     */
    public static WeightedScoreCalculator createDefault() {
        return new WeightedScoreCalculator(Arrays.asList(
                new GenreOverlapSubScore(),
                new CastOverlapSubScore(),
                new PopularitySubScore(),
                new FriendRatingSubScore(),
                new RecencySubScore()));
    }

    /**
     * Scores one candidate.
     *
     * @param candidate the candidate
     * @param context the context
     * @return the score
     */
    public ScoredMedia score(final Media candidate, final ScoringContext context) {
        final Map<String, Double> raw = new LinkedHashMap<>();
        final Map<String, Double> weighted = new LinkedHashMap<>();
        double total = 0.0;

        for (final SubScore subScore : this.subScores) {
            final double value = subScore.scoreFor(candidate, context);
            final double contribution = value * subScore.weightFrom(context.getWeights());
            raw.put(subScore.getName(), value);
            weighted.put(subScore.getName(), contribution);
            total += contribution;
        }

        return new ScoredMedia(candidate, total, new SubScoreBreakdown(raw, weighted));
    }
}
