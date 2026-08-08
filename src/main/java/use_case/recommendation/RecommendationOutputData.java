package use_case.recommendation;

import java.util.Collections;
import java.util.List;

/**
 * The result of a recommendation request.
 */
public class RecommendationOutputData {

    private final String username;
    private final List<RecommendedMedia> recommendations;
    private final List<GenreSection> sections;

    /**
     * Creates a result.
     *
     * @param username the username
     * @param recommendations the recommendations
     * @param sections the sections
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
     * @return the get username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the flat ranking.
     *
     * @return the get recommendations
     */
    public List<RecommendedMedia> getRecommendations() {
        return this.recommendations;
    }

    /**
     * Returns the genre sections.
     *
     * @return the get sections
     */
    public List<GenreSection> getSections() {
        return this.sections;
    }

    /**
     * Reports whether anything could be suggested.
     *
     * @return the is empty
     */
    public boolean isEmpty() {
        return this.recommendations.isEmpty();
    }
}
