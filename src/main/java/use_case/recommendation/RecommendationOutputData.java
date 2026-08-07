package use_case.recommendation;

import java.util.Collections;
import java.util.List;

/**
 * The result of a recommendation request.
 *
 * Holds both shapes at once: the flat ranking, which the home page uses, and the
 * genre sections, which the detailed view uses. Sections are empty when the
 * request did not ask for grouping, so neither presenter has to check what kind
 * of request produced the result.
 */
public class RecommendationOutputData {

    private final String username;
    private final List<RecommendedMedia> recommendations;
    private final List<GenreSection> sections;

    /**
     * Creates a result.
     *
     * @param username the user these are for
     * @param recommendations the flat ranking, best first
     * @param sections the same suggestions grouped by genre, empty if not requested
     */
    public RecommendationOutputData(final String username,
                                    final List<RecommendedMedia> recommendations,
                                    final List<GenreSection> sections) {
        this.username = username;
        this.recommendations = Collections.unmodifiableList(recommendations);
        this.sections = Collections.unmodifiableList(sections);
    }

    /**
     * Returns the user these suggestions are for.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the flat ranking.
     *
     * @return an unmodifiable list, best first
     */
    public List<RecommendedMedia> getRecommendations() {
        return this.recommendations;
    }

    /**
     * Returns the genre sections.
     *
     * @return an unmodifiable list, empty if grouping was not requested
     */
    public List<GenreSection> getSections() {
        return this.sections;
    }

    /**
     * Reports whether anything could be suggested.
     *
     * @return true if there are no suggestions at all
     */
    public boolean isEmpty() {
        return this.recommendations.isEmpty();
    }
}
