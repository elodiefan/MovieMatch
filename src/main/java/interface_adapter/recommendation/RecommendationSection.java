package interface_adapter.recommendation;

import java.util.ArrayList;
import java.util.List;

/**
 * Display-ready group of recommendations for the view model.
 */
public class RecommendationSection {

    private final String heading;
    private final List<RecommendationRow> recommendations;

    public RecommendationSection(final String heading,
                                 final List<RecommendationRow> recommendations) {
        this.heading = heading;
        this.recommendations = new ArrayList<>(recommendations);
    }

    public String getHeading() {
        return heading;
    }

    public List<RecommendationRow> getRecommendations() {
        return new ArrayList<>(recommendations);
    }
}
