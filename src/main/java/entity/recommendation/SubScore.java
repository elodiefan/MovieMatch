package entity.recommendation;

import entity.Media;

/**
 * One factor in a candidate's recommendation score.
 * <p>
 * The algorithm judges a candidate on five independent things — genre overlap,
 * cast overlap, popularity, what friends thought, and how recent it is. Each is
 * its own implementation of this interface, so a factor can be changed, tested,
 * or added without touching any of the others or the code that combines them.
 * <p>
 * Every implementation returns a value normalised to the range [0, 1], and must
 * return 0 rather than failing when the data it needs is missing — a title with
 * no listed cast, for example, simply scores zero for cast overlap.
 */
public interface SubScore {

    /**
     * Rates one candidate on this factor alone.
     *
     * @param candidate the media being considered
     * @param context the taste profile, friends' ratings, year and weights
     * @return a value in the range [0, 1]
     */
    double scoreFor(Media candidate, ScoringContext context);

    /**
     * Returns how much this factor counts, taken from the given weights.
     *
     * @param weights the weighting in force
     * @return this factor's weight
     */
    double weightFrom(ScoringWeights weights);

    /**
     * Returns a short label for this factor, used in score breakdowns and in
     * test failure messages.
     *
     * @return the factor's name
     */
    String getName();
}
