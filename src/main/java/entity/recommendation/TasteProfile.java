package entity.recommendation;

import java.util.Collections;
import java.util.Set;

import entity.Genre;

/**
 * What a user tends to enjoy, expressed as the genres and cast members that appear across the media they rated highly.
 */
public class TasteProfile {

    private final Set<Genre> genres;
    private final Set<String> cast;

    /** Creates a taste profile from already-collected genres and cast. */
    public TasteProfile(final Set<Genre> genres, final Set<String> cast) {
        this.genres = Collections.unmodifiableSet(genres);
        this.cast = Collections.unmodifiableSet(cast);
    }

    /** Returns the genres in this profile. */
    public Set<Genre> getGenres() {
        return this.genres;
    }

    /** Returns the cast and crew in this profile. */
    public Set<String> getCast() {
        return this.cast;
    }

    /** Reports whether this profile contains nothing to compare against. */
    public boolean isEmpty() {
        return this.genres.isEmpty() && this.cast.isEmpty();
    }
}
