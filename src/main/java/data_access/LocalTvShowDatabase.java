package data_access;

import java.util.List;

import entity.TVShow;

/**
 * A local database storing TV show information.
 */
public class LocalTvShowDatabase {

    private final List<TVShow> tvShows;

    /**
     * Creates a local TV show database.
     *
     * @param tvShows the TV shows stored inside the database
     */
    public LocalTvShowDatabase(List<TVShow> tvShows) {
        this.tvShows = tvShows;
    }

    /**
     * Returns all TV shows stored in the database.
     *
     * @return all TV shows
     */
    public List<TVShow> getTvShows() {
        return tvShows;
    }
}
