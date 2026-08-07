package entity.recommendation;

import java.util.Collections;
import java.util.Set;

import entity.Genre;

/**
 * What a user tends to enjoy, expressed as the genres and cast members that
 * appear across the media they rated highly.
 *
 * This is the result of section 2 of the recommendation algorithm: the union of
 * genres and the union of cast across every title the user gave at least four
 * stars. Candidates are later scored by how much they overlap with it.
 *
 * Instances are immutable; build one with {@link TasteProfileBuilder}.
 */
public class TasteProfile {

    private final Set<Genre> genres;
    private final Set<String> cast;

    /**
     * Creates a taste profile from already-collected genres and cast.
     *
     * @param genres the genres the user has rated highly
     * @param cast the cast and crew the user has rated highly
     */
    public TasteProfile(final Set<Genre> genres, final Set<String> cast) {
        this.genres = Collections.unmodifiableSet(genres);
        this.cast = Collections.unmodifiableSet(cast);
    }

    /**
     * Returns the genres in this profile.
     *
     * @return an unmodifiable set of genres
     */
    public Set<Genre> getGenres() {
        return this.genres;
    }

    /**
     * Returns the cast and crew in this profile.
     *
     * @return an unmodifiable set of cast member names
     */
    public Set<String> getCast() {
        return this.cast;
    }

    /**
     * Reports whether this profile contains nothing to compare against.
     *
     * A brand-new user who has not rated anything highly has an empty profile,
     * which means genre and cast overlap can only ever score zero. Callers use
     * this to fall back on popularity and recency instead.
     *
     * @return true if the profile has no genres and no cast
     */
    public boolean isEmpty() {
        return this.genres.isEmpty() && this.cast.isEmpty();
    }
}
