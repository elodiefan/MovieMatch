package use_case.recommendation;

import entity.Media;
import entity.recommendation.TasteProfile;

/**
 * Refines a candidate's deterministic score by a small amount.
 * <p>
 * This is the only place an outside intelligence is allowed to touch the
 * ranking, and it is deliberately narrow: an implementation returns one small
 * number and one sentence. It cannot see candidates that did not already score
 * well, cannot reorder the list itself, and cannot move a weak title above a
 * strong one — the caller clamps whatever comes back.
 * <p>
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
}
