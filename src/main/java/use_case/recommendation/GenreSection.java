package use_case.recommendation;

import java.util.Collections;
import java.util.List;

/** A run of suggestions shown under one heading, such as "Because you like Sci-Fi". */
public class GenreSection {

    private final String genreName;
    private final List<RecommendedMedia> recommendations;

    /** Creates a section. */
    public GenreSection(final String genreName, final List<RecommendedMedia> recommendations) {
        this.genreName = genreName;
        this.recommendations = Collections.unmodifiableList(recommendations);
    }

    /** Returns the genre this section covers. */
    public String getGenreName() {
        return this.genreName;
    }

    /** Returns the suggestions in this section. */
    public List<RecommendedMedia> getRecommendations() {
        return this.recommendations;
    }

    /** Returns the heading a view should show above this section. */
    public String getHeading() {
        return "Because you like " + this.genreName;
    }
}
