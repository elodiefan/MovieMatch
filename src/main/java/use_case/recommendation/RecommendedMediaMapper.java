package use_case.recommendation;

import java.util.ArrayList;
import java.util.List;

import entity.Genre;
import entity.Media;
import entity.recommendation.ScoredMedia;
import entity.recommendation.TasteProfile;

/**
 * Turns scored entities into the shape a screen can display.
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
     * @param scored the scored
     * @param profile the profile
     * @return the to recommended media
     */
    public RecommendedMedia toRecommendedMedia(final ScoredMedia scored, final TasteProfile profile) {
        final Media media = scored.getMedia();
        return new RecommendedMedia(
                media.getID(),
                media.getTitle(),
                media.getReleaseYear(),
                scored.getScore(),
                this.pickPrimaryGenre(media, profile),
                scored.getExplanation(),
                media.getPosterPath());
    }

    /**
     * Converts a whole list.
     *
     * @param scored the scored
     * @param profile the profile
     * @return the to recommended media
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
     * @param media the media
     * @param profile the profile
     * @return the pick primary genre
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
