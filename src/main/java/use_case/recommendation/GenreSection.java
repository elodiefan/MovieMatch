package use_case.recommendation;

import java.util.Collections;
import java.util.List;

/**
 * A run of suggestions shown under one heading, such as "Because you like Sci-Fi".
 */
public class GenreSection {

    private final String genreName;
    private final List<RecommendedMedia> recommendations;

    /**
     * Creates a section.
     *
     * @param genreName the genre name
     * @param recommendations the recommendations
     */
    public GenreSection(final String genreName, final List<RecommendedMedia> recommendations) {
        this.genreName = genreName;
        this.recommendations = Collections.unmodifiableList(recommendations);
    }

    /**
     * Returns the genre this section covers.
     *
     * @return the get genre name
     */
    public String getGenreName() {
        return this.genreName;
    }

    /**
     * Returns the suggestions in this section.
     *
     * @return the get recommendations
     */
    public List<RecommendedMedia> getRecommendations() {
        return this.recommendations;
    }

    /**
     * Returns the heading a view should show above this section.
     *
     * @return the get heading
     */
    public String getHeading() {
        return "Because you like " + this.genreName;
    }
}
