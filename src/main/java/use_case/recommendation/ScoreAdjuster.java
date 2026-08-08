package use_case.recommendation;

import java.util.ArrayList;
import java.util.List;

import entity.Media;
import entity.recommendation.TasteProfile;

/**
 * Refines a candidate's deterministic score by a small amount.
 */
public interface ScoreAdjuster {

    /**
     * Suggests a small shift to one candidate's score.
     *
     * @param candidate the candidate
     * @param tasteProfile the taste profile
     * @return the adjust
     */
    Adjustment adjust(Media candidate, TasteProfile tasteProfile);

    /**
     * Suggests a shift for every candidate on the shortlist at once.
     *
     * @param candidates the candidates
     * @param tasteProfile the taste profile
     * @return the adjust all
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
