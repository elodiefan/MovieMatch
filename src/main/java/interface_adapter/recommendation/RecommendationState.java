package interface_adapter.recommendation;

import java.util.ArrayList;
import java.util.List;

import use_case.recommendation.GenreSection;
import use_case.recommendation.RecommendedMedia;

/** What the recommendation screens draw. */
public class RecommendationState {

    private List<RecommendedMedia> recommendations = new ArrayList<>();
    private List<GenreSection> sections = new ArrayList<>();
    private String recommendationError;
    private String username = "";

    /** Distinguishes "nothing to suggest" from "not asked yet". */
    private boolean loaded;

    public List<RecommendedMedia> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendedMedia> recommendations) {
        this.recommendations = recommendations;
    }

    public List<GenreSection> getSections() {
        return sections;
    }

    public void setSections(List<GenreSection> sections) {
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
