package use_case.recommendation;

import java.util.List;
import java.util.Set;

import entity.Genre;
import entity.Media;

/**
 * Where candidate titles come from.
 */
public interface MediaCatalogueDataAccessInterface {

    /**
     * Returns titles worth considering for a user.
     *
     * The catalogue is asked to leave adult titles out rather than being handed
     * them to discard afterwards, because whether a title counts as adult is
     * something only the source knows.
     *
     * @param genres the genres
     * @param excludeMediaIds the exclude media ids
     * @param allowAdultContent whether adult titles may be returned
     * @return the find candidates
     */
    List<Media> findCandidates(Set<Genre> genres, Set<Integer> excludeMediaIds,
                               boolean allowAdultContent);

    /**
     * Looks up one title by id.
     *
     * @param mediaId the media id
     * @return the find by id
     */
    Media findById(int mediaId);
}
