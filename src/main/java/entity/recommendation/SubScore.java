package entity.recommendation;

import entity.Media;

/** One factor in a candidate's recommendation score. */
public interface SubScore {

    /** Rates one candidate on this factor alone. */
    double scoreFor(Media candidate, ScoringContext context);

    /** Returns how much this factor counts, taken from the given weights. */
    double weightFrom(ScoringWeights weights);

    /** Returns a short label for this factor, used in score breakdowns and in test failure messages. */
    String getName();
}
