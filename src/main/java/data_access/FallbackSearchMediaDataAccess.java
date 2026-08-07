package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.Media;
import use_case.search.MediaPage;
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
     * Searches one TMDB page and uses the complete local result set as the
     * first and only page when TMDB is unavailable.
     *
     * @param keyword keyword entered by the user
     * @param page page number starting at one
     * @return one page of matching media
     */
    @Override
    public MediaPage searchPage(String keyword, int page) {
        MediaPage result;

        try {
            result = tmdbSearchMediaDataAccess.searchPage(keyword, page);
        }
        catch (IllegalStateException exception) {
            final List<Media> localResults;
            if (page == 1) {
                localResults = searchLocalDatabases(keyword);
            }
            else {
                localResults = new ArrayList<>();
            }
            result = new MediaPage(
                    localResults,
                    1,
                    localResults.size()
            );
        }

        return result;
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
