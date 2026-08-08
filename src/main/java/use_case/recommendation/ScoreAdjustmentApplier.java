package use_case.recommendation;

import java.util.ArrayList;
import java.util.List;

import entity.recommendation.ScoredMedia;
import entity.recommendation.TasteProfile;

/**
 * Lets an adjuster refine the ranking, within strict limits.
 */
public class ScoreAdjustmentApplier {

    /**
     * The most an adjuster may move a score in either direction.
     */
    public static final double MAX_ADJUSTMENT = 0.05;

    private final ScoreAdjuster adjuster;

    /**
     * Creates an applier wrapping the given adjuster.
     *
     * @param adjuster the adjuster
     */
    public ScoreAdjustmentApplier(final ScoreAdjuster adjuster) {
        this.adjuster = adjuster;
    }

    /**
     * Applies clamped adjustments to a shortlist.
     *
     * @param shortlist the shortlist
     * @param profile the profile
     * @return the apply to
     */
    public List<ScoredMedia> applyTo(final List<ScoredMedia> shortlist, final TasteProfile profile) {
        final List<entity.Media> candidates = new ArrayList<>();
        for (final ScoredMedia scored : shortlist) {
            candidates.add(scored.getMedia());
        }

        // Asked for in one go rather than one at a time, which is what makes
        // this affordable enough to run on every load.
        List<Adjustment> adjustments;
        try {
            adjustments = this.adjuster.adjustAll(candidates, profile);
        }
        catch (ScoreAdjustmentException ex) {
            adjustments = new ArrayList<>();
        }

        final List<ScoredMedia> adjusted = new ArrayList<>();
        for (int i = 0; i < shortlist.size(); i++) {
            final Adjustment adjustment;
            if (i < adjustments.size() && adjustments.get(i) != null) {
                adjustment = adjustments.get(i);
            }
            else {
                // An adjuster that returned a short list, or none at all, leaves
                // the deterministic ranking in place for the rest.
                adjustment = Adjustment.NONE;
            }
            adjusted.add(this.applyOne(shortlist.get(i), adjustment));
        }
        return adjusted;
    }

    /**
     * Adjusts a single result, falling back to no change if the adjuster fails.
     *
     * @param scored the scored
     * @param adjustment the adjustment
     * @return the apply one
     */
    private ScoredMedia applyOne(final ScoredMedia scored, final Adjustment adjustment) {
        // Clamped here, and again inside ClampingScoreAdjuster, which is the
        // two places the algorithm document asks for.
        final double clamped = Math.max(-MAX_ADJUSTMENT,
                Math.min(MAX_ADJUSTMENT, adjustment.getDelta()));
        return scored.withAdjustment(clamped, adjustment.getExplanation());
    }
}
