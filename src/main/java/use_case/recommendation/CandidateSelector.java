package use_case.recommendation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import entity.Media;
import entity.recommendation.TasteProfile;

/**
 * Works out which titles are worth scoring for a user.
 */
public class CandidateSelector {

    private final MediaCatalogueDataAccessInterface catalogue;

    /**
     * Creates a selector reading from the given catalogue.
     *
     * @param catalogue the catalogue
     */
    public CandidateSelector(final MediaCatalogueDataAccessInterface catalogue) {
        this.catalogue = catalogue;
    }

    /**
     * Returns the titles worth scoring for a user.
     *
     * @param profile the profile
     * @param alreadyRated the already rated
     * @param allowAdultContent whether adult titles may be offered
     * @return the select for
     */
    public List<Media> selectFor(final TasteProfile profile, final List<UserRating> alreadyRated,
                                 final boolean allowAdultContent) {
        final Set<Integer> seen = alreadyRated.stream()
                .map(UserRating::getMediaId)
                .collect(Collectors.toCollection(HashSet::new));
        return this.catalogue.findCandidates(profile.getGenres(), seen, allowAdultContent);
    }
}
