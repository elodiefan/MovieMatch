package use_case.recommendation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import entity.Media;
import entity.recommendation.TasteProfile;

/**
 * Works out which titles are worth scoring for a user.
 * <p>
 * Step 3 of the algorithm. Two things decide it: the pool is narrowed to genres
 * the user already likes so the search stays manageable, and anything they have
 * already rated is removed, because a recommendation the user has seen is not a
 * recommendation.
 * <p>
 * Kept apart from the interactor because "what counts as a candidate" is a rule
 * likely to grow — excluding blocked users' favourites, or titles already on a
 * watchlist — and that growth should not make the interactor bigger.
 */
public class CandidateSelector {

    private final MediaCatalogueDataAccessInterface catalogue;

    /**
     * Creates a selector reading from the given catalogue.
     *
     * @param catalogue where candidate titles come from
     */
    public CandidateSelector(final MediaCatalogueDataAccessInterface catalogue) {
        this.catalogue = catalogue;
    }

    /**
     * Returns the titles worth scoring for a user.
     *
     * @param profile the genres and cast the user is known to like
     * @param alreadyRated everything the user has rated, high or low
     * @return candidate titles, excluding anything already rated
     */
    public List<Media> selectFor(final TasteProfile profile, final List<UserRating> alreadyRated) {
        final Set<Integer> seen = alreadyRated.stream()
                .map(UserRating::getMediaId)
                .collect(Collectors.toCollection(HashSet::new));
        return this.catalogue.findCandidates(profile.getGenres(), seen);
    }
}
