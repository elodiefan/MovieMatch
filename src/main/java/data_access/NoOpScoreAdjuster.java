package data_access;

import entity.Media;
import entity.recommendation.TasteProfile;
import use_case.recommendation.Adjustment;
import use_case.recommendation.ScoreAdjuster;

/**
 * An adjuster that never changes anything.
 *
 * Used when no AI is configured. The algorithm document specifies exactly this
 * behaviour as the fallback — "the adjustment silently defaults to 0 and the
 * deterministic ranking is what the user sees, unchanged" — so this is the
 * specified behaviour rather than a placeholder.
 *
 * Having it as a real object means nothing downstream needs a null check.
 */
public class NoOpScoreAdjuster implements ScoreAdjuster {

    /**
     * Creates the adjuster.
     */
    public NoOpScoreAdjuster() {
        // Stateless.
    }

    @Override
    public Adjustment adjust(final Media candidate, final TasteProfile tasteProfile) {
        return Adjustment.NONE;
    }
}
