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
     * @param genres the genres
     * @param excludeMediaIds the exclude media ids
     * @return the find candidates
     */
    List<Media> findCandidates(Set<Genre> genres, Set<Integer> excludeMediaIds);

    /**
     * Looks up one title by id.
     *
     * @param mediaId the media id
     * @return the find by id
     */
    Media findById(int mediaId);
}
