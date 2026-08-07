package use_case.recommendation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Arranges suggestions into "Because you like ..." sections. */
public class GenreGrouper {

    /** Creates a grouper. */
    public GenreGrouper() {
        // Stateless: everything needed arrives per call.
    }

    /** Groups suggestions by the genre each was filed under. */
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
