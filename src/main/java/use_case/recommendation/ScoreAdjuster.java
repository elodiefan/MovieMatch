package use_case.recommendation;

import java.util.ArrayList;
import java.util.List;

import entity.Media;
import entity.recommendation.TasteProfile;

/**
 * Refines a candidate's deterministic score by a small amount.
 *
 * This is the only place an outside intelligence is allowed to touch the
 * ranking, and it is deliberately narrow: an implementation returns one small
 * number and one sentence. It cannot see candidates that did not already score
 * well, cannot reorder the list itself, and cannot move a weak title above a
 * strong one — the caller clamps whatever comes back.
 *
 * Implementations that reach a network throw {@link ScoreAdjustmentException}
 * rather than returning a wrong answer, so the caller can fall back cleanly to
 * the deterministic ranking.
 */
public interface ScoreAdjuster {

    /**
     * Suggests a small shift to one candidate's score.
     *
     * @param candidate the title being considered
     * @param tasteProfile what the user is known to enjoy
     * @return the suggested shift and an explanation
     * @throws ScoreAdjustmentException if no usable adjustment could be produced
     */
    Adjustment adjust(Media candidate, TasteProfile tasteProfile);

    /**
     * Suggests a shift for every candidate on the shortlist at once.
     *
     * Asking about the whole shortlist in one go is what keeps this step
     * affordable. Adjusting a ten title list one at a time meant a dozen
     * separate round trips, which took twenty seconds and tripped rate limits
     * badly enough that some titles came back with no explanation at all.
     *
     * The default implementation asks about each candidate in turn, so an
     * adjuster with nothing to gain from batching need not implement it. A
     * candidate whose adjustment cannot be produced gets Adjustment.NONE, so
     * one failure never costs the whole shortlist.
     *
     * @param candidates the shortlist, in ranked order
     * @param tasteProfile what the user is known to enjoy
     * @return one adjustment per candidate, in the same order
     */
    default List<Adjustment> adjustAll(List<Media> candidates, TasteProfile tasteProfile) {
        final List<Adjustment> adjustments = new ArrayList<>();
        for (final Media candidate : candidates) {
            Adjustment adjustment;
            try {
                adjustment = adjust(candidate, tasteProfile);
            }
            catch (ScoreAdjustmentException exception) {
                adjustment = Adjustment.NONE;
            }
            adjustments.add(adjustment);
        }
        return adjustments;
    }
}
