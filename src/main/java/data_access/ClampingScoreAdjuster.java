package data_access;

import entity.Media;
import entity.recommendation.TasteProfile;
import use_case.recommendation.Adjustment;
import use_case.recommendation.ScoreAdjuster;

/**
 * Wraps another adjuster and refuses to let it move a score very far.
 *
 * The algorithm document requires the adjustment to be clamped in two places,
 * "once by the interactor and again defensively inside the implementation". This
 * is the second of those: whatever an adjuster returns — a misread reply, a
 * misbehaving model, a bug — cannot escape the permitted range before it reaches
 * the use case.
 *
 * Wrapping rather than editing each adjuster means the guarantee holds for every
 * implementation, including ones written later.
 */
public class ClampingScoreAdjuster implements ScoreAdjuster {

    /** The most any adjuster may move a score in either direction. */
    public static final double MAX_ADJUSTMENT = 0.05;

    private final ScoreAdjuster delegate;

    /**
     * Wraps an adjuster.
     *
     * @param delegate the adjuster whose output should be clamped
     */
    public ClampingScoreAdjuster(final ScoreAdjuster delegate) {
        this.delegate = delegate;
    }

    @Override
    public Adjustment adjust(final Media candidate, final TasteProfile tasteProfile) {
        final Adjustment raw = this.delegate.adjust(candidate, tasteProfile);
        final double clamped = Math.max(-MAX_ADJUSTMENT,
                Math.min(MAX_ADJUSTMENT, raw.getDelta()));
        return new Adjustment(clamped, raw.getExplanation());
    }
}
