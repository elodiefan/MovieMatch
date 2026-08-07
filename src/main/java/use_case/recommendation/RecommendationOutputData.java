package use_case.recommendation;

import java.util.Collections;
import java.util.List;

/** The result of a recommendation request. */
public class RecommendationOutputData {

    private final String username;
    private final List<RecommendedMedia> recommendations;
    private final List<GenreSection> sections;

    /** Creates a result. */
    public RecommendationOutputData(final String username,
                                    final List<RecommendedMedia> recommendations,
                                    final List<GenreSection> sections) {
        this.username = username;
        this.recommendations = Collections.unmodifiableList(recommendations);
        this.sections = Collections.unmodifiableList(sections);
    }

    /** Returns the user these suggestions are for. */
    public String getUsername() {
        return this.username;
    }

    /** Returns the flat ranking. */
    public List<RecommendedMedia> getRecommendations() {
        return this.recommendations;
    }

    /** Returns the genre sections. */
    public List<GenreSection> getSections() {
        return this.sections;
    }

    public int getRecommendationCount() {
        return recommendations.size();
    }

    public int getRecommendationMediaId(final int index) {
        return recommendations.get(index).getMediaId();
    }

    public String getRecommendationTitle(final int index) {
        return recommendations.get(index).getTitle();
    }

    public int getRecommendationReleaseYear(final int index) {
        return recommendations.get(index).getReleaseYear();
    }

    public double getRecommendationScore(final int index) {
        return recommendations.get(index).getScore();
    }

    public String getRecommendationPrimaryGenre(final int index) {
        return recommendations.get(index).getPrimaryGenre();
    }

    public String getRecommendationExplanation(final int index) {
        return recommendations.get(index).getExplanation();
    }

    public int getSectionCount() {
        return sections.size();
    }

    public String getSectionHeading(final int sectionIndex) {
        return sections.get(sectionIndex).getHeading();
    }

    public int getSectionRecommendationCount(final int sectionIndex) {
        return sections.get(sectionIndex).getRecommendations().size();
    }

    public int getSectionRecommendationMediaId(final int sectionIndex,
                                               final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getMediaId();
    }

    public String getSectionRecommendationTitle(final int sectionIndex,
                                                final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getTitle();
    }

    public int getSectionRecommendationReleaseYear(final int sectionIndex,
                                                   final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getReleaseYear();
    }

    public double getSectionRecommendationScore(final int sectionIndex,
                                                final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getScore();
    }

    public String getSectionRecommendationPrimaryGenre(final int sectionIndex,
                                                       final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getPrimaryGenre();
    }

    public String getSectionRecommendationExplanation(final int sectionIndex,
                                                      final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getExplanation();
    }

    /** Reports whether anything could be suggested. */
    public boolean isEmpty() {
        return this.recommendations.isEmpty();
    }
}
