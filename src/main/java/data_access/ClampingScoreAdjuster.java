package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.Media;
import entity.recommendation.TasteProfile;
import use_case.recommendation.Adjustment;
import use_case.recommendation.ScoreAdjuster;

/**
 * Wraps another adjuster and refuses to let it move a score very far.
 */
public class ClampingScoreAdjuster implements ScoreAdjuster {

    /**
     * The most any adjuster may move a score in either direction.
     */
    public static final double MAX_ADJUSTMENT = 0.05;

    private final ScoreAdjuster delegate;

    /**
     * Wraps an adjuster.
     *
     * @param delegate the delegate
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
     * @param candidates the candidates
     * @param tasteProfile the taste profile
     * @return the adjust all
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
