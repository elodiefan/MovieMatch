package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.Media;
import use_case.search.SearchMediaDataAccess;

/**
 * Searches TMDB first and uses the local databases when TMDB fails.
 */
public class FallbackSearchMediaDataAccess
        implements SearchMediaDataAccess {

    private final SearchMediaDataAccess tmdbSearchMediaDataAccess;
    private final LocalMovieDatabase localMovieDatabase;
    private final LocalTvShowDatabase localTvShowDatabase;

    /**
     * Creates a search data access object with a local fallback.
     *
     * @param tmdbSearchMediaDataAccess primary TMDB data access
     * @param localMovieDatabase local movie database
     * @param localTvShowDatabase local TV show database
     */
    public FallbackSearchMediaDataAccess(
            SearchMediaDataAccess tmdbSearchMediaDataAccess,
            LocalMovieDatabase localMovieDatabase,
            LocalTvShowDatabase localTvShowDatabase) {

        this.tmdbSearchMediaDataAccess =
                tmdbSearchMediaDataAccess;
        this.localMovieDatabase = localMovieDatabase;
        this.localTvShowDatabase = localTvShowDatabase;
    }

    /**
     * Searches TMDB and falls back to local JSON when TMDB fails.
     *
     * @param keyword keyword entered by the user
     * @return matching media
     */
    @Override
    public List<Media> search(String keyword) {
        List<Media> results;

        try {
            results = tmdbSearchMediaDataAccess.search(keyword);
        }
        catch (IllegalStateException exception) {
            results = searchLocalDatabases(keyword);
        }

        return results;
    }

    /**
     * Searches both local databases and combines their results.
     *
     * @param keyword keyword entered by the user
     * @return matching local media
     */
    private List<Media> searchLocalDatabases(String keyword) {
        final List<Media> results = new ArrayList<>();

        results.addAll(localMovieDatabase.search(keyword));
        results.addAll(localTvShowDatabase.search(keyword));

        return results;
    }
}
