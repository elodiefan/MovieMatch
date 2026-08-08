package entity.recommendation;

import java.util.HashSet;
import java.util.Set;

import entity.Genre;
import entity.Media;

/**
 * Collects a user's highly-rated media into a {@link TasteProfile}.
 */
public class TasteProfileBuilder {

    /**
     * A rating at or above this counts as "highly rated" — algorithm section 1.
     */
    public static final double HIGHLY_RATED_THRESHOLD = 4.0;

    private final Set<Genre> genres = new HashSet<>();
    private final Set<String> cast = new HashSet<>();

    /**
     * Creates an empty builder.
     */
    public TasteProfileBuilder() {
        // Nothing to set up; media is added through add().
    }

    /**
     * Folds one rated title into the profile, if the user rated it highly enough.
     *
     * @param media the media
     * @param rating the rating
     * @return the add
     */
    public TasteProfileBuilder add(final Media media, final double rating) {
        if (rating >= HIGHLY_RATED_THRESHOLD) {
            this.genres.addAll(media.getGenres());
            this.cast.addAll(media.getCast());
        }
        return this;
    }

    /**
     * Produces the profile accumulated so far.
     *
     * @return the build
     */
    public TasteProfile build() {
        return new TasteProfile(new HashSet<>(this.genres), new HashSet<>(this.cast));
    }
}
