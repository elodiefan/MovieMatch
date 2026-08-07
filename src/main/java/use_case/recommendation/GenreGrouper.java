package use_case.recommendation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Arranges suggestions into "Because you like ..." sections.
 *
 * What makes the detailed view more useful than a flat ranking: the same
 * results, clustered so the user can see the reasoning. Sections are ordered by
 * their strongest suggestion, so the most compelling cluster appears first, and
 * within each section the order from the ranking is preserved.
 */
public class GenreGrouper {

    /**
     * Creates a grouper.
     */
    public GenreGrouper() {
        // Stateless: everything needed arrives per call.
    }

    /**
     * Groups suggestions by the genre each was filed under.
     *
     * @param recommendations the suggestions, already ranked best first
     * @return sections ordered by their best suggestion, each preserving rank order
     */
    public List<GenreSection> group(final List<RecommendedMedia> recommendations) {
        // A linked map keeps first-seen order, which — because the input is
        // already ranked — is exactly "best section first".
        final Map<String, List<RecommendedMedia>> byGenre = new LinkedHashMap<>();
        for (final RecommendedMedia recommendation : recommendations) {
            byGenre.computeIfAbsent(recommendation.getPrimaryGenre(), key -> new ArrayList<>())
                    .add(recommendation);
        }

        final List<GenreSection> sections = new ArrayList<>();
        for (final Map.Entry<String, List<RecommendedMedia>> entry : byGenre.entrySet()) {
            sections.add(new GenreSection(entry.getKey(), entry.getValue()));
        }
        return sections;
    }
}
