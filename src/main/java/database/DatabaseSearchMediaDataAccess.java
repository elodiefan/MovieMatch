package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import entity.Media;
import entity.Movie;
import entity.TVShow;
import use_case.search.MediaPage;
import use_case.search.SearchMediaDataAccess;

public class DatabaseSearchMediaDataAccess
        implements SearchMediaDataAccess {

    private final LocalMovieDatabase movieDatabase;
    private final LocalTvShowDatabase tvShowDatabase;

    public DatabaseSearchMediaDataAccess(
            LocalMovieDatabase movieDatabase,
            LocalTvShowDatabase tvShowDatabase
    ) {
        this.movieDatabase = movieDatabase;
        this.tvShowDatabase = tvShowDatabase;
    }

    @Override
    public List<Media> search(String keyword) {

        List<Media> result;

        try {
            result = searchFromTmdb(keyword);
        }

        catch (IOException event) {
            result = searchFromLocal(keyword);
        }

        return result;
    }

    /**
     * The local databases are not paged, so everything is one page.
     *
     * @param keyword the keyword
     * @param page the page
     * @return the search page
     */
    @Override
    public MediaPage searchPage(String keyword, int page) {
        final List<Media> results;
        if (page > 1) {
            results = new ArrayList<>();
        }
        else {
            results = search(keyword);
        }
        return new MediaPage(results, 1, results.size());
    }

    private boolean matches(Media media, String[] words) {
        final String title = media.getTitle().toLowerCase(Locale.ROOT);
        boolean result = true;

        for (String word : words) {
            if (!title.contains(word)) {
                result = false;
            }
        }

        return result;
    }

    private List<Media> searchFromTmdb(String keyword) throws IOException {
        throw new IOException("TMDB API not implemented yet.");
    }

    private List<Media> searchFromLocal(String keyword) {

        final List<Media> result = new ArrayList<>();

        final String[] words = keyword.toLowerCase(Locale.ROOT)
                .split("\\s+");

        for (Movie movie : movieDatabase.getMovies()) {
            if (matches(movie, words)) {
                result.add(movie);
            }
        }

        for (TVShow tvShow : tvShowDatabase.getTvShows()) {
            if (matches(tvShow, words)) {
                result.add(tvShow);
            }
        }

        return result;
    }
}
