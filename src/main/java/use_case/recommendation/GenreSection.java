package use_case.recommendation;

import java.util.Collections;
import java.util.List;

/**
 * A run of suggestions shown under one heading, such as "Because you like Sci-Fi".
 *
 * Grouping is what separates the detailed view from the home page list: the same
 * scored results, but organised so the user can see why each cluster was chosen
 * rather than reading one flat ranking.
 */
public class GenreSection {

    private final String genreName;
    private final List<RecommendedMedia> recommendations;

    /**
     * Creates a section.
     *
     * @param genreName the genre this section is built around
     * @param recommendations the suggestions in it, best first
     */
    public GenreSection(final String genreName, final List<RecommendedMedia> recommendations) {
        this.genreName = genreName;
        this.recommendations = Collections.unmodifiableList(recommendations);
    }

    /**
     * Returns the genre this section covers.
     *
     * @return the genre name
     */
    public String getGenreName() {
        return this.genreName;
    }

    /**
     * Returns the suggestions in this section.
     *
     * @return an unmodifiable list, best first
     */
    public List<RecommendedMedia> getRecommendations() {
        return this.recommendations;
    }

    /**
     * Returns the heading a view should show above this section.
     *
     * @return a phrase such as "Because you like Sci-Fi"
     */
    public String getHeading() {
        return "Because you like " + this.genreName;
    }
}
