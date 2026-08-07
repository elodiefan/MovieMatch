package use_case.recommendation;

import java.util.ArrayList;
import java.util.List;

import entity.Genre;
import entity.Media;
import entity.recommendation.ScoredMedia;
import entity.recommendation.TasteProfile;

/**
 * Turns scored entities into the shape a screen can display.
 *
 * The boundary between the two worlds: {@link ScoredMedia} carries entities and
 * a factor breakdown, while {@link RecommendedMedia} carries plain strings and
 * numbers. Doing the conversion in one place means presenters never reach into
 * entities, which is what keeps the layers apart.
 *
 * Follows the same mapper convention as {@code ReviewSummaryMapper} elsewhere in
 * the project.
 */
public class RecommendedMediaMapper {

    private static final String NO_GENRE = "Other";

    /**
     * Creates a mapper.
     */
    public RecommendedMediaMapper() {
        // Stateless: everything needed arrives per call.
    }

    /**
     * Converts one scored result.
     *
     * @param scored the scored candidate
     * @param profile the user's taste, used to pick which genre to file it under
     * @return a display-ready suggestion
     */
    public RecommendedMedia toRecommendedMedia(final ScoredMedia scored, final TasteProfile profile) {
        final Media media = scored.getMedia();
        return new RecommendedMedia(
                media.getID(),
                media.getTitle(),
                media.getReleaseYear(),
                scored.getScore(),
                this.pickPrimaryGenre(media, profile),
                scored.getExplanation());
    }

    /**
     * Converts a whole list.
     *
     * @param scored the scored candidates, in the order they should appear
     * @param profile the user's taste profile
     * @return display-ready suggestions in the same order
     */
    public List<RecommendedMedia> toRecommendedMedia(final List<ScoredMedia> scored,
                                                     final TasteProfile profile) {
        final List<RecommendedMedia> mapped = new ArrayList<>();
        for (final ScoredMedia one : scored) {
            mapped.add(this.toRecommendedMedia(one, profile));
        }
        return mapped;
    }

    /**
     * Chooses which of a title's genres to file it under.
     *
     * Prefers one the user already likes, so a science-fiction thriller lands
     * under "Because you like Sci-Fi" for a science-fiction fan and under
     * "Because you like Thriller" for someone else. Falls back to the title's
     * first genre when nothing matches.
     *
     * @param media the title
     * @param profile the user's taste
     * @return the genre name to group under
     */
    private String pickPrimaryGenre(final Media media, final TasteProfile profile) {
        String chosen = NO_GENRE;
        for (final Genre genre : media.getGenres()) {
            if (profile.getGenres().contains(genre)) {
                chosen = genre.getName();
                break;
            }
        }
        if (NO_GENRE.equals(chosen) && !media.getGenres().isEmpty()) {
            chosen = media.getGenres().get(0).getName();
        }
        return chosen;
    }
}
