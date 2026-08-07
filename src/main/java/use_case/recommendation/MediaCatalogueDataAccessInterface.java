package use_case.recommendation;

import java.util.List;
import java.util.Set;

import entity.Genre;
import entity.Media;

/** Where candidate titles come from. */
public interface MediaCatalogueDataAccessInterface {

    /** Returns titles worth considering for a user. */
    List<Media> findCandidates(Set<Genre> genres, Set<Integer> excludeMediaIds);

    /** Looks up one title by id. */
    Media findById(int mediaId);
}
