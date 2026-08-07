package data_access;

import java.util.ArrayList;
import java.util.List;

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
        return clamp(this.delegate.adjust(candidate, tasteProfile));
    }

    /**
     * Passes a whole shortlist through and clamps every answer.
     *
     * Overridden rather than inherited so the guarantee still holds when a
     * delegate answers in bulk. Without this the default would fall back to
     * asking one at a time and the batching would be lost.
     */
    @Override
    public List<Adjustment> adjustAll(final List<Media> candidates, final TasteProfile tasteProfile) {
        final List<Adjustment> clamped = new ArrayList<>();
        for (final Adjustment raw : this.delegate.adjustAll(candidates, tasteProfile)) {
            clamped.add(clamp(raw));
        }
        return clamped;
    }

    private Adjustment clamp(final Adjustment raw) {
        final double capped = Math.max(-MAX_ADJUSTMENT,
                Math.min(MAX_ADJUSTMENT, raw.getDelta()));
        return new Adjustment(capped, raw.getExplanation());
    }
}
