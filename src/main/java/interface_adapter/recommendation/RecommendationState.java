package interface_adapter.recommendation;

import java.util.ArrayList;
import java.util.List;

/**
 * What the recommendation screens draw.
 */
public class RecommendationState {

    private List<RecommendationRow> recommendations = new ArrayList<>();
    private List<RecommendationSection> sections = new ArrayList<>();
    private String recommendationError;
    private String username = "";

    /**
     * Distinguishes "nothing to suggest" from "not asked yet".
     */
    private boolean loaded;

    public List<RecommendationRow> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationRow> recommendations) {
        this.recommendations = recommendations;
    }

    public List<RecommendationSection> getSections() {
        return sections;
    }

    public void setSections(List<RecommendationSection> sections) {
        this.sections = sections;
    }

    public String getRecommendationError() {
        return recommendationError;
    }

    public void setRecommendationError(String recommendationError) {
        this.recommendationError = recommendationError;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
