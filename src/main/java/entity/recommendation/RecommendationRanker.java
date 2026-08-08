package entity.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Puts scored candidates in order and keeps the best few.
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
     * @param scored the scored
     * @param limit the limit
     * @return the rank
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
