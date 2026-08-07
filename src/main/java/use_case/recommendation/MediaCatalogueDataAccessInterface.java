package use_case.recommendation;

import java.util.List;
import java.util.Set;

import entity.Genre;
import entity.Media;

/**
 * Where candidate titles come from.
 * <p>
 * Kept separate from {@link RecommendationDataAccessInterface} because the two
 * are backed by different things: people live in the app's own database, whereas
 * the catalogue comes from TMDB or a local copy of it. Splitting them means the
 * catalogue can be swapped without touching user storage.
 */
public interface MediaCatalogueDataAccessInterface {

    /**
     * Returns titles worth considering for a user.
     * <p>
     * Narrowing by genre keeps the search space manageable, as the algorithm
     * document suggests. An empty genre set means the user has no taste profile
     * yet, in which case implementations should return a general pool rather
     * than nothing — otherwise a new user would see no suggestions at all.
     *
     * @param genres the genres the user is known to like; may be empty
     * @param excludeMediaIds titles to leave out, normally everything the user
     *                        has already rated
     * @return candidate titles, never null
     */
    List<Media> findCandidates(Set<Genre> genres, Set<Integer> excludeMediaIds);

    /**
     * Looks up one title by id.
     *
     * @param mediaId the title wanted
     * @return the title, or null if the catalogue does not have it
     */
    Media findById(int mediaId);
}
