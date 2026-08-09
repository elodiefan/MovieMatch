package entity.recommendation;

import entity.Media;

/**
 * One factor in a candidate's recommendation score.
 */
public interface SubScore {

    /**
     * Rates one candidate on this factor alone.
     *
     * @param candidate the candidate
     * @param context the context
     * @return the score for
     */
    double scoreFor(Media candidate, ScoringContext context);

    /**
     * Returns how much this factor counts, taken from the given weights.
     *
     * @param weights the weights
     * @return the weight from
     */
    double weightFrom(ScoringWeights weights);

    /**
     * Returns a short label for this factor, used in score breakdowns and in test failure messages.
     *
     * @return the get name
     */
    String getName();
}
