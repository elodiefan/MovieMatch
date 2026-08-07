package database;

import entity.Media;
import entity.recommendation.TasteProfile;
import use_case.recommendation.Adjustment;
import use_case.recommendation.ScoreAdjuster;

/** An adjuster that never changes anything. */
public class NoOpScoreAdjuster implements ScoreAdjuster {

    /** Creates the adjuster. */
    public NoOpScoreAdjuster() {
        // Stateless.
    }

    @Override
    public Adjustment adjust(final Media candidate, final TasteProfile tasteProfile) {
        return Adjustment.NONE;
    }
}
