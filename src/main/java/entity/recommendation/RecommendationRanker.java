package entity.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Puts scored candidates in order and keeps the best few.
 *
 * Steps 6 and 7 of the algorithm. Separated from the scoring itself because how
 * many results to show is a presentation decision that differs per screen — the
 * home page wants a handful, the dedicated view wants more — while the scores
 * behind them are identical.
 */
public class RecommendationRanker {

    /**
     * Creates a ranker.
     */
    public RecommendationRanker() {
        // Stateless: the limit is supplied per call.
    }

    /**
     * Sorts by score, highest first, and keeps the top few.
     *
     * @param scored the candidates to rank, in any order
     * @param limit how many to keep; values below one yield an empty list
     * @return a new list holding at most limit results, best first
     */
    public List<ScoredMedia> rank(final List<ScoredMedia> scored, final int limit) {
        final List<ScoredMedia> ordered = new ArrayList<>(scored);
        Collections.sort(ordered);
        final List<ScoredMedia> result;
        if (limit < 1) {
            result = new ArrayList<>();
        }
        else {
            result = new ArrayList<>(ordered.subList(0, Math.min(limit, ordered.size())));
        }
        return result;
    }
}
