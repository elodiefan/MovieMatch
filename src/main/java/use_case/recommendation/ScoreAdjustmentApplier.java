package use_case.recommendation;

import java.util.ArrayList;
import java.util.List;

import entity.recommendation.ScoredMedia;
import entity.recommendation.TasteProfile;

/**
 * Lets an adjuster refine the ranking, within strict limits.
 *
 * Enforces the three rules that keep an outside intelligence from taking over
 * the recommendation. Only the shortlist is offered to it, so anything that
 * scored poorly stays invisible. Whatever comes back is clamped to a small
 * range, so a strange answer can at most reorder titles that were already close
 * together. And if the adjuster fails, the adjustment silently becomes zero and
 * the user sees the deterministic ranking unchanged.
 */
public class ScoreAdjustmentApplier {

    /** The most an adjuster may move a score in either direction. */
    public static final double MAX_ADJUSTMENT = 0.05;

    private final ScoreAdjuster adjuster;

    /**
     * Creates an applier wrapping the given adjuster.
     *
     * @param adjuster the adjuster to consult
     */
    public ScoreAdjustmentApplier(final ScoreAdjuster adjuster) {
        this.adjuster = adjuster;
    }

    /**
     * Applies clamped adjustments to a shortlist.
     *
     * @param shortlist the best candidates so far, already ranked
     * @param profile what the user is known to enjoy
     * @return a new list with adjusted scores and explanations attached
     */
    public List<ScoredMedia> applyTo(final List<ScoredMedia> shortlist, final TasteProfile profile) {
        final List<ScoredMedia> adjusted = new ArrayList<>();
        for (final ScoredMedia scored : shortlist) {
            adjusted.add(this.adjustOne(scored, profile));
        }
        return adjusted;
    }

    /**
     * Adjusts a single result, falling back to no change if the adjuster fails.
     *
     * @param scored the result to adjust
     * @param profile what the user is known to enjoy
     * @return the result with any clamped adjustment applied
     */
    private ScoredMedia adjustOne(final ScoredMedia scored, final TasteProfile profile) {
        Adjustment adjustment;
        try {
            adjustment = this.adjuster.adjust(scored.getMedia(), profile);
        }
        catch (ScoreAdjustmentException ex) {
            // The deterministic ranking is a complete answer on its own, so a
            // failed adjuster is not worth failing the whole request over.
            adjustment = Adjustment.NONE;
        }
        final double clamped = Math.max(-MAX_ADJUSTMENT,
                Math.min(MAX_ADJUSTMENT, adjustment.getDelta()));
        return scored.withAdjustment(clamped, adjustment.getExplanation());
    }
}
