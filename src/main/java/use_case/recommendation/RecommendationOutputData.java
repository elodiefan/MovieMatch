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
     * Returns the number of recommended media items.
     * @return the recommendation count
     */
    public int getRecommendationCount() {
        return recommendations.size();
    }

    /**
     * Returns the media id for a recommendation.
     * @param index the recommendation index
     * @return the media id
     */
    public int getRecommendationMediaId(final int index) {
        return recommendations.get(index).getMediaId();
    }

    /**
     * Returns the title for a recommendation.
     * @param index the recommendation index
     * @return the title
     */
    public String getRecommendationTitle(final int index) {
        return recommendations.get(index).getTitle();
    }

    /**
     * Returns the release year for a recommendation.
     * @param index the recommendation index
     * @return the release year
     */
    public int getRecommendationReleaseYear(final int index) {
        return recommendations.get(index).getReleaseYear();
    }

    /**
     * Returns the score for a recommendation.
     * @param index the recommendation index
     * @return the recommendation score
     */
    public double getRecommendationScore(final int index) {
        return recommendations.get(index).getScore();
    }

    /**
     * Returns the primary genre for a recommendation.
     * @param index the recommendation index
     * @return the primary genre
     */
    public String getRecommendationPrimaryGenre(final int index) {
        return recommendations.get(index).getPrimaryGenre();
    }

    /**
     * Returns the explanation for a recommendation.
     * @param index the recommendation index
     * @return the explanation
     */
    public String getRecommendationExplanation(final int index) {
        return recommendations.get(index).getExplanation();
    }

    /**
     * Returns the number of recommendation sections.
     * @return the section count
     */
    public int getSectionCount() {
        return sections.size();
    }

    /**
     * Returns the heading for a section.
     * @param sectionIndex the section index
     * @return the section heading
     */
    public String getSectionHeading(final int sectionIndex) {
        return sections.get(sectionIndex).getHeading();
    }

    /**
     * Returns the number of recommendations in a section.
     * @param sectionIndex the section index
     * @return the recommendation count
     */
    public int getSectionRecommendationCount(final int sectionIndex) {
        return sections.get(sectionIndex).getRecommendations().size();
    }

    /**
     * Returns the media id for a section recommendation.
     * @param sectionIndex the section index
     * @param recommendationIndex the recommendation index
     * @return the media id
     */
    public int getSectionRecommendationMediaId(final int sectionIndex,
                                               final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getMediaId();
    }

    /**
     * Returns the title for a section recommendation.
     * @param sectionIndex the section index
     * @param recommendationIndex the recommendation index
     * @return the title
     */
    public String getSectionRecommendationTitle(final int sectionIndex,
                                                final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getTitle();
    }

    /**
     * Returns the release year for a section recommendation.
     * @param sectionIndex the section index
     * @param recommendationIndex the recommendation index
     * @return the release year
     */
    public int getSectionRecommendationReleaseYear(final int sectionIndex,
                                                   final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getReleaseYear();
    }

    /**
     * Returns the score for a section recommendation.
     * @param sectionIndex the section index
     * @param recommendationIndex the recommendation index
     * @return the recommendation score
     */
    public double getSectionRecommendationScore(final int sectionIndex,
                                                final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getScore();
    }

    /**
     * Returns the primary genre for a section recommendation.
     * @param sectionIndex the section index
     * @param recommendationIndex the recommendation index
     * @return the primary genre
     */
    public String getSectionRecommendationPrimaryGenre(final int sectionIndex,
                                                       final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getPrimaryGenre();
    }

    /**
     * Returns the explanation for a section recommendation.
     * @param sectionIndex the section index
     * @param recommendationIndex the recommendation index
     * @return the explanation
     */
    public String getSectionRecommendationExplanation(final int sectionIndex,
                                                      final int recommendationIndex) {
        return sections.get(sectionIndex).getRecommendations()
                .get(recommendationIndex).getExplanation();
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
